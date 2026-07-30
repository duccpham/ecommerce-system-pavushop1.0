import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Input, Button, Alert, Layout } from "antd";
import Header from "@/user/components/header/Header";
import fetchWithAuth from "@/services/fetchWithAuth";
const Register = () => {
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const onFinish = async (values) => {
    try {
      const response = await fetchWithAuth("/api/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(values),
      });

      const data = await response.text();
      if (response.ok) {
        setMessage("Tạo tài khoản thành công");
        setTimeout(() => navigate("/login"), 2000);
      } else {
        setError(data);
      }
    } catch (err) {
      setError("Có lỗi gì đó trong lúc tạo tài khoản");
    }
  };

  return (
    <Layout>
      <Header />
      <div style={{ maxWidth: 400, margin: "auto", padding: 50 }}>
        <h2>Register</h2>
        {error && <Alert message={error} type="error" showIcon closable />}
        {message && <Alert message={message} type="success" showIcon closable />}
        <Form name="register" onFinish={onFinish} layout="vertical">
          <Form.Item label="Full Name" name="fullName" rules={[{ required: true, message: "Please enter your full name" }]}>
            <Input />
          </Form.Item>
          <Form.Item label="Username" name="userName" rules={[{ required: true, message: "Please enter your username" }]}>
            <Input />
          </Form.Item>
          <Form.Item label="Email" name="email" rules={[{ required: true, type: "email", message: "Please enter a valid email" }]}>
            <Input />
          </Form.Item>
          <Form.Item label="Password" name="userPassword" rules={[{ required: true, message: "Please enter your password" }]}>
            <Input.Password />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" block>
              Register
            </Button>
          </Form.Item>
        </Form>
      </div>
    </Layout>
  );
};

export default Register;
