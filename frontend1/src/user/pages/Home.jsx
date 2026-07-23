import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Card, Typography, Row, Col, Button, Divider } from "antd";
import { Layout } from 'antd';
import Header from "@/user/components/header/Header";
import fetchWithAuth from '@/services/fetchWithAuth';
const { Title } = Typography;

const Home = () => {
  const [newProducts, setNewProducts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchHome();
  }, []);

  const fetchHome = async () => {
    try {
      const res = await fetchWithAuth("/api/home");
      const data = await res.json();
      setNewProducts(data.productList || []);
    } catch (error) {
      console.error("Lỗi khi tìm danh sách sản phẩm:", error);
    }

  }

  const handleProductClick = (productId) => {
    navigate(`/productDetail/${productId}`);
  };

  const handleAddToCart = async (productId) => {
    try {
      const res = await fetchWithAuth(`/api/cart/add/${productId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        }
      })

      if (res.ok) {
        alert("Sản phẩm đã được thêm vào giỏ hàng!");
      } else {
        alert("Thêm vào giỏ hàng thất bại. Phải đăng nhập để tạo giỏ hàng");
      }
    } catch (error) {
      console.error("Lỗi khi thêm sản phẩm vào giỏ hàng", error);
      alert("Thêm vào giỏ hàng thất bại. Phải đăng nhập để tạo giỏ hàng");
    };
  };

  const renderProductCard = (product) => (
    <Col span={6} key={product.productId}>
      <Card
        hoverable
        cover={
          <img
            alt={product.name}
            src={`http://localhost:8080/images/${product.image}`}
            style={{
              width: "100%",
              height: "auto",
              maxHeight: "250px",
              objectFit: "contain",
              background: "#fff",
              padding: "8px"
            }}
          />
        }
        onClick={() => handleProductClick(product.productId)}
        actions={[
          <Button
            type="primary"
            onClick={(e) => {
              e.stopPropagation();
              handleAddToCart(product.productId);
            }}
          >
            Add to Cart
          </Button>
        ]}
      >
        <Card.Meta
          title={
            <a onClick={() => handleProductClick(product.productId)}>
              {product.name}
            </a>
          }
          description={`$${product.price}`}
        />
      </Card>
    </Col>
  );

  return (
    <Layout>
      <Header />
      <div style={{ backgroundColor: '#379683', padding: '40px 20px' }}>
        <Title
          level={2}
          style={{
            backgroundColor: '#CEBC81',
            padding: '40px 20px',
            fontFamily: 'Arial',
            textAlign: 'center'
          }}
        >
          Top 8 sản phẩm mới nhất
        </Title>
        <Row gutter={[16, 16]} justify="center">
          {newProducts.map((product) => renderProductCard(product))}
        </Row>
        <Divider style={{ margin: '40px 0' }} />
      </div>
    </Layout>
  );
};

export default Home;
