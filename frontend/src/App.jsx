import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from './user/pages/Home';
import ProductDetail from './user/pages/ProductDetail';
import CartItems from './user/pages/CartItem';
import Login from './user/pages/Login';
import Register from './user/pages/Register';
import Checkout from './user/pages/Checkout';
import Shop from './user/pages/Shop/Shop';
import AdminLayout from './admin/layouts/AdminLayout';
import Category from './admin/pages/Category';
import Brand from './admin/pages/Brand';
import Product from './admin/pages/Product'
import Order from './admin/pages/Order';
import Customer from './admin/pages/Customer';
import Report from './admin/pages/Report';

function App() {
  return (

    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/products" element={<Shop />} />
      <Route path="/searchProduct/:keyword" element={<Shop />} />
      <Route path="/productDetail/:id" element={<ProductDetail />} />
      <Route path="/cart/items" element={< CartItems />} />
      <Route path="/cart/checkout" element={< Checkout />} />
      <Route path="/login" element={< Login />} />
      <Route path="/register" element={< Register />} />

      <Route path="/admin" element={<AdminLayout />}>
        <Route path="category" element={<Category />} />
        <Route path="brand" element={<Brand />} />
        <Route path="product" element={<Product />} />
        <Route path="order" element={<Order />} />
        <Route path="customer" element={<Customer />} />
        <Route path="stat-product" element={<Report />} />
        <Route path="stat-category" element={<Report />} />
        <Route path="stat-brand" element={<Report />} />
        <Route path="stat-year" element={<Report />} />
        <Route path="stat-quarter" element={<Report />} />
        <Route path="stat-customer" element={<Report />} />
      </Route>

    </Routes>


  );
}

export default App;