import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { message, Button, Table } from "antd";
import { Layout } from 'antd';
import Header from "@/user/components/header/Header";
import fetchWithAuth from '@/services/fetchWithAuth';

const ShoppingCart = () => {
  const [cartData, setCartData] = useState({ cartItems: [], totalCartItems: 0 });
  const navigate = useNavigate();

  useEffect(() => {
    fetchCartItem();
  }, []);

  const fetchCartItem = async () => {
    try {
      const res = await fetchWithAuth("/api/cart");
      const data = await res.json();
      setCartData({ cartItems: data, totalCartItems: data.length });
    } catch (error) {
      console.error('Không thể load cart', error);
    }
  }

  const removeFromCart = async (id) => {
    try {
      const res = await fetchWithAuth(`/api/cart/remove/${id}`, {
        method: "DELETE"
      });
      if (res.ok) {
        fetchCartItem();
      }
    } catch (error) {
      console.error('Xóa sản phẩm thất bại', error);
    }

  }

  const columns = [
    {
      title: "Sản phẩm",
      dataIndex: "product",
      key: "product",
      render: (product) => (
        <div style={{ display: "flex", alignItems: "center" }}>
          <img
            src={`http://localhost:8080/images/${product.image}`}
            alt={product.name}
            style={{
              width: "80px",
              height: "80px",
              objectFit: "contain",
              background: "#fff",
              padding: "8px",
              borderRadius: "8px",
              marginRight: "16px"
            }}
          />
          <span style={{ fontWeight: "bold", fontSize: "16px" }}>{product.name}</span>
        </div>
      ),
    },
    {
      title: "Số lượng",
      dataIndex: "quantity",
      key: "quantity",
      render: (quantity) => <span style={{ fontSize: "16px" }}>{quantity}</span>,
    },
    {
      title: "Hành động",
      key: "action",
      render: (_, record) => (
        <Button danger onClick={() => removeFromCart(record.id)}>
          Xóa
        </Button>
      ),
    },
  ];

  return (
    <Layout>
      <Header />
      <div style={{ padding: "40px", maxWidth: "1000px", margin: "0 auto" }}>
        <h2 style={{ textAlign: "center", marginBottom: "30px", fontSize: "28px" }}>
          Giỏ hàng của bạn
        </h2>
        {cartData.cartItems.length === 0 ? (
          <p style={{ textAlign: "center", fontSize: "18px" }}>Giỏ hàng đang trống.</p>
        ) : (
          <Table
            columns={columns}
            dataSource={cartData.cartItems}
            rowKey="id"
            pagination={false}
          />
        )}
        <div style={{ textAlign: "center", marginTop: "40px" }}>
          <Button
            type="primary"
            size="large"
            style={{ marginRight: "20px" }}
            onClick={() => navigate("/cart/checkout")}
          >
            Thanh toán
          </Button>
          <Button size="large" onClick={() => navigate("/")}>
            Quay lại trang chủ
          </Button>
        </div>
      </div>
    </Layout>
  );
};

export default ShoppingCart;
