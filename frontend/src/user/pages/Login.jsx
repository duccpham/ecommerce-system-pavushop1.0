import React, { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Input, Button, Card, Typography, message, Layout, Modal } from "antd";
import { AuthContext } from "@/context/AuthContext";
import Header from "@/user/components/header/Header";
import fetchWithAuth from '@/services/fetchWithAuth';

const { Title } = Typography;

const Login = () => {
  const { setCustomer } = useContext(AuthContext);
  const [forgotOpen, setForgotOpen] = useState(false);
  const [otpSent, setOtpSent] = useState(false);
  const [countdown, setCountdown] = useState(120);
  const [forgotLoading, setForgotLoading] = useState(false);
  const [forgotForm] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (values) => {
    setLoading(true);
    try {
      const response = await fetchWithAuth("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          username: values.username.trim(),
          password: values.password.trim(),
        }),
      });

      if (!response.ok) {
        message.error("Tên đăng nhập hoặc mật khẩu sai!");
        return;
      }

      const data = await response.json();
      message.success("Đăng nhập thành công!");
      localStorage.setItem("customer", JSON.stringify(data));
      setCustomer(data);

      setTimeout(() => {
        if (data.role === "ROLE_ADMIN") {
          navigate("/admin");
        } else {
          navigate("/");
        }
      }, 0);
    } catch (error) {
      console.error("Lỗi khi đăng nhập:", error);
      message.error("Đã xảy ra lỗi khi đăng nhập!");
    } finally {
      setLoading(false);
    }
  };

  const openForgotModal = () => {
    forgotForm.resetFields();
    setOtpSent(false);
    setForgotOpen(true);
  };

  const closeForgotModal = () => {
    forgotForm.resetFields();
    setOtpSent(false);
    setForgotOpen(false);
  };

  const handleForgotPassword = async () => {
    try {

      const values = await forgotForm.validateFields();
      setForgotLoading(true);

      const response = await fetchWithAuth(
        "/api/forgot-password",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: values.email.trim(),
          }),
        }
      );

      const text = await response.text();
      if (!response.ok) {
        message.error(text);
        return;
      }
      message.success(text);
      setOtpSent(true);
      setCountdown(120);

    } catch (error) {
      console.error(error);
      if (!error.errorFields) {
        message.error("Không thể gửi OTP");
      }

    } finally {
      setForgotLoading(false);
    }
  };

  const handleResendOtp = async () => {
    try {
      const email = forgotForm.getFieldValue("email");

      setForgotLoading(true);
      const response = await fetchWithAuth(
        "/api/forgot-password",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email,
          }),
        }
      );

      const text = await response.text();
      if (!response.ok) {
        message.error(text);
        return;
      }
      message.success("OTP mới đã được gửi");
      setCountdown(120);
    } catch (error) {
      console.error(error);
      message.error("Không thể gửi lại OTP");
    } finally {
      setForgotLoading(false);
    }

  };

  const handleResetPassword = async () => {
    try {
      const values = await forgotForm.validateFields();
      if (values.newPassword !== values.confirmPassword) {
        message.error("Xác nhận mật khẩu không khớp");
        return;
      }
      setForgotLoading(true);
      const response = await fetchWithAuth(
        "/api/reset-password",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: values.email,
            otp: values.otp,
            newPassword: values.newPassword,
          }),
        }
      );
      const text = await response.text();
      if (!response.ok) {
        message.error(text);
        return;
      }
      message.success(text);
      closeForgotModal();
    } catch (error) {
      console.error(error);
      message.error("Đổi mật khẩu thất bại");
    } finally {
      setForgotLoading(false);
    }
  };

  useEffect(() => {

    if (!otpSent) return;
    if (countdown <= 0) return;
    const timer = setInterval(() => {
      setCountdown((prev) => prev - 1);
    }, 1000);
    return () => clearInterval(timer);
  }, [otpSent, countdown]);

  return (
    <Layout>
      <Header />
      <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
        <Card style={{ width: 350, padding: "20px", boxShadow: "0 0 10px rgba(0,0,0,0.1)" }}>
          <Title level={3} style={{ textAlign: "center" }}>Đăng nhập</Title>
          <Form layout="vertical" onFinish={handleLogin}>
            <Form.Item
              label="Tên đăng nhập"
              name="username"
              rules={[{ required: true, message: "Vui lòng nhập tên đăng nhập!" }]}
            >
              <Input placeholder="Nhập tên đăng nhập" />
            </Form.Item>
            <Form.Item
              label="Mật khẩu"
              name="password"
              rules={[{ required: true, message: "Vui lòng nhập mật khẩu!" }]}
            >
              <Input.Password placeholder="Nhập mật khẩu" />
            </Form.Item>
            <Form.Item style={{ marginBottom: 10 }}>
              <Button
                type="link"
                style={{ padding: 0 }}
                onClick={openForgotModal}
              >
                Quên mật khẩu?
              </Button>
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" block loading={loading}>
                Đăng nhập
              </Button>
            </Form.Item>
          </Form>
        </Card>
        <Modal
          title="Quên mật khẩu"
          open={forgotOpen}
          onCancel={closeForgotModal}
          footer={null}
          destroyOnClose
        >
          <Form
            form={forgotForm}
            layout="vertical">
            <Form.Item
              label="Email"
              name="email"
              rules={[
                {
                  required: true,
                  message: "Vui lòng nhập email",
                },
                {
                  type: "email",
                  message: "Email không hợp lệ",
                },
              ]}
            >
              <Input
                disabled={otpSent}
                placeholder="Nhập email"
              />
            </Form.Item>
            {otpSent && (
              <>
                <Form.Item
                  label="OTP"
                  name="otp"
                  rules={[
                    {
                      required: true,
                      message: "Nhập OTP",
                    },
                  ]}
                >
                  <Input placeholder="Nhập OTP" />
                </Form.Item>
                <Form.Item>

                  <Button
                    type="link"
                    disabled={countdown > 0}
                    onClick={handleResendOtp}
                    style={{ padding: 0 }}
                  >
                    {countdown > 0
                      ? `Gửi lại OTP sau ${countdown}s`
                      : "Gửi lại OTP"}
                  </Button>

                </Form.Item>
                <Form.Item
                  label="Mật khẩu mới"
                  name="newPassword"
                  rules={[
                    {
                      required: true,
                      message: "Nhập mật khẩu mới",
                    },
                    {
                      min: 6,
                      message: "Ít nhất 6 ký tự",
                    },
                  ]}
                >
                  <Input.Password />
                </Form.Item>
                <Form.Item
                  label="Xác nhận mật khẩu"
                  name="confirmPassword"
                  dependencies={["newPassword"]}
                  rules={[
                    {
                      required: true,
                      message: "Nhập lại mật khẩu",
                    },
                    ({ getFieldValue }) => ({
                      validator(_, value) {
                        if (
                          !value ||
                          getFieldValue("newPassword") === value
                        ) {
                          return Promise.resolve();
                        }
                        return Promise.reject(
                          new Error("Mật khẩu xác nhận không khớp")
                        );
                      },
                    }),
                  ]}
                >
                  <Input.Password />
                </Form.Item>
              </>)}
            <Form.Item>
              {!otpSent ? (
                <Button
                  type="primary"
                  block
                  loading={forgotLoading}
                  onClick={handleForgotPassword}
                >
                  Gửi OTP
                </Button>
              ) : (
                <Button
                  type="primary"
                  block
                  loading={forgotLoading}
                  onClick={handleResetPassword}
                >
                  Đổi mật khẩu
                </Button>
              )}
            </Form.Item>
          </Form>
        </Modal>
      </div>

    </Layout>
  );
};

export default Login;
