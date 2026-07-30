import React, { useState, useEffect } from 'react';
import { Layout, Menu, Row, Col, Card, Button } from 'antd';
import { Content } from 'antd/es/layout/layout';
import Header from "@/user/components/header/Header";
import Sider from 'antd/es/layout/Sider';
import { useNavigate, useParams } from 'react-router-dom';
import './Shop.css';
import fetchWithAuth from '@/services/fetchWithAuth';

const Shop1 = () => {
  const [products, setProducts] = useState([]);
  const [brands, setBrands] = useState([]);
  const [categories, setCategories] = useState([]);
  const [countProduct, setCountProduct] = useState({});
  const [selectedBrandKey, setSelectedBrandKey] = useState('');
  const [selectedCategoryKey, setSelectedCategoryKey] = useState('');
  const { keyword } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (keyword) {
      fetchSearch();
    } else {
      fetchProducts();
      setSelectedBrandKey('');
      setSelectedCategoryKey('');
    }
  }, [keyword]);

  const fetchSearch = async () => {
    try {
      const res = await fetchWithAuth(`/api/shop/searchProduct?keyword=${encodeURIComponent(keyword)}`, {
        method: 'GET'
      });
      const data = await res.json();
      setProducts(data.productList);
      setBrands(data.brandList);
      setCategories(data.categoryList);
      setCountProduct(data.countProductByCategory || {});
      setSelectedBrandKey('');
      setSelectedCategoryKey('');
    } catch (error) {
      console.error("Tìm kiếm thất bại:", error);
    }
  }


  const fetchProducts = async () => {
    const res = await fetchWithAuth("/api/shop/products", {
      method: "GET"
    });
    const data = await res.json();
    setProducts(data.productList);
    setBrands(data.brandList);
    setCategories(data.categoryList);
    setCountProduct(data.countProductByCategory || {});
  };

  const fetchProductByBrand = async (brandId) => {
    try {
      const res = await fetchWithAuth(`/api/shop/productByBrand/${brandId}`, {
        method: "GET"
      });
      const data = await res.json();
      setProducts(data.productList);
    } catch (error) {
      console.error("Lỗi khi lấy sản phẩm theo brand:", error);
    }
  };

  const fetchProductByCategory = async (categoryId) => {
    const res = await fetchWithAuth(`/api/shop/productByCategory/${categoryId}`, {
      method: 'GET'
    });
    const data = await res.json();
    setProducts(data.productList);
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

  const handleProductClick = (productId) => {
    navigate(`/productDetail/${productId}`);
  };

  return (
    <Layout style={{ backgroundColor: '#379683', padding: '40px 20px' }}>
      <Header />
      <Layout>
        <Sider width={250} style={{ background: '#fff', padding: '16px 8px' }}>
          <h3>Thương hiệu</h3>
          <Menu
            mode="inline"
            selectedKeys={[selectedBrandKey]}
            onClick={(e) => {
              const brandId = e.key;
              setSelectedBrandKey(brandId);
              fetchProductByBrand(brandId);
              setSelectedCategoryKey('');
            }}
          >
            {brands.map((brand) => (
              <Menu.Item key={brand.brandId}>{brand.brandName}</Menu.Item>
            ))}
          </Menu>

          <h3 style={{ marginTop: '20px' }}>Danh mục</h3>
          <Menu
            mode="inline"
            selectedKeys={[selectedCategoryKey]}
            onClick={(e) => {
              const categoryId = e.key;
              setSelectedCategoryKey(categoryId);
              fetchProductByCategory(categoryId);
              setSelectedBrandKey('');
            }}
          >
            {categories.map((category) => (
              <Menu.Item key={category.categoryId}>
                {category.categoryName} ({countProduct[category.categoryId] || 0})
              </Menu.Item>
            ))}
          </Menu>
        </Sider>

        <Layout style={{ backgroundColor: '#379683', padding: '16px' }}>
          <Content>
            <Row gutter={[16, 16]}>
              {products.map((product) => (
                <Col key={product.productId} xs={24} sm={12} md={8} lg={6}>
                  <Card
                    hoverable
                    cover={
                      <div
                        style={{
                          width: "100%",
                          height: "250px",
                          background: "#fff",
                          padding: "8px",
                          display: "flex",
                          alignItems: "center",
                          justifyContent: "center"
                        }}
                      >
                        <img
                          alt={product.name}
                          src={`http://localhost:8080/images/${product.image}`}
                          style={{
                            maxWidth: "100%",
                            maxHeight: "100%",
                            objectFit: "contain"
                          }}
                        />
                      </div>


                    }
                  >
                    <h3
                      onClick={() => handleProductClick(product.productId)}
                      style={{ cursor: 'pointer' }}
                    >
                      {product.name}
                    </h3>
                    <p><strong>Giá:</strong> {product.price.toLocaleString()}₫</p>
                    <p><strong>Kho:</strong> {product.quantity}</p>
                    <p><strong>Giảm giá:</strong> {product.discount}%</p>
                    <p><strong>Ngày nhập:</strong> {new Date(product.enteredDate).toLocaleDateString()}</p>
                    <p><strong>Danh mục:</strong> {product.category?.categoryName}</p>
                    <p><strong>Thương hiệu:</strong> {product.brand?.brandName}</p>
                    <Button
                      type="primary"
                      onClick={(e) => {
                        e.stopPropagation();
                        handleAddToCart(product.productId);
                      }}
                    >
                      Add to Cart
                    </Button>
                  </Card>
                </Col>
              ))}
            </Row>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
};

export default Shop1;
