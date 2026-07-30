import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Card, Typography, Button, message, Layout, Row, Col, Pagination
} from "antd";
import Header from "@/user/components/header/Header";
import fetchWithAuth from "@/services/fetchWithAuth";

const { Title, Paragraph } = Typography;

const ProductDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [product, setProduct] = useState(null);
  const [relatedProducts, setRelatedProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  const pageSize = 4;

  useEffect(() => {
    fetchProductDetail();
  }, [id]);

  const fetchProductDetail = async () => {
    try {
      const res = await fetchWithAuth(`/api/productDetail/${id}`);
      const data = await res.json();

      setProduct(data.product);
      setRelatedProducts(data.productByCategory || []);
      setCurrentPage(1);
    } catch (error) {
      console.error("Lỗi khi load thông tin chi tiết sản phẩm:", error);
    }
  };

  const handleProductClick = (productId) => {
    navigate(`/productDetail/${productId}`);
  };

  const handleAddToCart = async (productId) => {
    try {
      const res = await fetchWithAuth(
        `/api/cart/add/${productId}`,
        {
          method: "POST"
        }
      );

      if (res.ok) {
        alert("Sản phẩm đã được thêm vào giỏ hàng!");
      } else {
        alert("Thêm vào giỏ hàng thất bại. Phải đăng nhập để tạo giỏ hàng");
      }
    } catch (error) {
      console.error("Có lỗi khi thêm sản phẩm vào giỏ hàng:", error);
      message.error("Failed to add product to cart.");
    }
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  const paginatedProducts = relatedProducts.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize
  );

  if (!product) {
    return <Title level={3}>Loading...</Title>;
  }

  return (
    <Layout style={{ backgroundColor: "#379683", minHeight: "100vh" }}>
      <Header />

      <div style={{ padding: "40px 20px" }}>
        <div
          style={{
            backgroundColor: "#fff",
            padding: "24px",
            borderRadius: "8px"
          }}
        >
          <Row gutter={24}>
            <Col span={8}>
              <img
                alt={product.name}
                src={`http://localhost:8080/images/${product.image}`}
                style={{
                  width: "100%",
                  maxHeight: "350px",
                  objectFit: "contain",
                  background: "#fff",
                  padding: "8px"
                }}
              />
            </Col>

            <Col span={16}>
              <Card bordered={false}>
                <Title level={3}>{product.name}</Title>

                <Paragraph>
                  <strong>Price:</strong> ${product.price}
                </Paragraph>

                <Paragraph>
                  <strong>Description:</strong> {product.description}
                </Paragraph>

                <Button
                  type="primary"
                  size="large"
                  onClick={() => handleAddToCart(product.productId)}
                >
                  Add To Cart
                </Button>
              </Card>
            </Col>
          </Row>

          <Title level={4} style={{ marginTop: "40px" }}>
            Sản phẩm liên quan
          </Title>

          <Row gutter={[16, 16]}>
            {paginatedProducts.map((item) => (
              <Col span={6} key={item.productId}>
                <Card
                  hoverable
                  style={{ cursor: "pointer" }}
                  onClick={() => handleProductClick(item.productId)}
                  cover={
                    <img
                      alt={item.name}
                      src={`http://localhost:8080/images/${item.image}`}
                      style={{
                        width: "100%",
                        height: "200px",
                        objectFit: "contain",
                        background: "#fff",
                        padding: "8px"
                      }}
                    />
                  }
                  actions={[
                    <Button
                      type="primary"
                      onClick={(e) => {
                        e.stopPropagation();
                        handleAddToCart(item.productId);
                      }}
                    >
                      Add To Cart
                    </Button>
                  ]}
                >
                  <Card.Meta
                    title={item.name}
                    description={`$${item.price}`}
                  />
                </Card>
              </Col>
            ))}
          </Row>

          <div style={{ textAlign: "center", marginTop: "20px" }}>
            <Pagination
              current={currentPage}
              pageSize={pageSize}
              total={relatedProducts.length}
              onChange={handlePageChange}
            />
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default ProductDetail;