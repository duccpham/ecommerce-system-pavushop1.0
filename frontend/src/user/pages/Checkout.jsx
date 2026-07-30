import React, { useState } from "react";
import { Form, Input, Button, message, Layout } from "antd";
import { useNavigate } from "react-router-dom";
import Header from "@/user/components/header/Header";
import fetchWithAuth from '@/services/fetchWithAuth';

const { Content } = Layout;

const Checkout = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const response = await fetchWithAuth("/api/cart/checkout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(values),
      });

      if (!response.ok) {
        message.error("Lỗi khi đặt hàng");
        return;
      }

      const data = await response.json();
      const orderId = data;
      console.log("Order ID:", orderId);
      message.success(`Đặt hàng thành công! Mã đơn hàng: ${orderId}`, 2);

      setTimeout(() => {
        navigate("/");
      }, 2000);
    } catch (error) {
      console.error(error);
      message.error("Đặt hàng thất bại, vui lòng thử lại");
    } finally {
      setLoading(false);
    }
  };


  return (
    <Layout>
      <Header />
      <Content style={{ padding: "40px", maxWidth: "600px", margin: "0 auto" }}>
        <h2 style={{ textAlign: "center", marginBottom: "30px" }}>Thông tin đặt hàng</h2>
        <Form layout="vertical" onFinish={onFinish}>
          <Form.Item
            label="Họ và tên"
            name="receiver"
            rules={[{ required: true, message: "Vui lòng nhập họ tên" }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Địa chỉ"
            name="address"
            rules={[{ required: true, message: "Vui lòng nhập địa chỉ" }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="Số điện thoại"
            name="phone"
            rules={[
              { required: true, message: "Vui lòng nhập số điện thoại" },
              { pattern: /^\d{10}$/, message: "Số điện thoại phải gồm 10 chữ số" },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item label="Ghi chú (tuỳ chọn)" name="description">
            <Input.TextArea rows={4} />
          </Form.Item>

          <Form.Item style={{ textAlign: "center" }}>
            <Button type="primary" htmlType="submit" loading={loading}>
              Đặt hàng
            </Button>
          </Form.Item>
        </Form>
      </Content>
    </Layout>
  );
};

export default Checkout;
