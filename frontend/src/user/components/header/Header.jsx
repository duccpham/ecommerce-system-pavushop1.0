import React, { useState, useEffect, useContext } from "react";
import { useNavigate, NavLink } from "react-router-dom";
import {
  SearchOutlined,
  ShoppingCartOutlined,
} from "@ant-design/icons";
import { Input } from "antd";
import { AuthContext } from "@/context/AuthContext";
import "./Header.css";
import fetchWithAuth from "@/services/fetchWithAuth";

const Header = () => {
  const { customer, setCustomer } = useContext(AuthContext);

  const [cartCount, setCartCount] = useState(0);
  const [keyword, setKeyword] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    fetchCount();
  }, []);

  const fetchCount = async () => {
    try {
      const res = await fetchWithAuth("/api/cart/count");
      const data = await res.json();
      setCartCount(data);
    } catch (error) {
      console.error("Lỗi khi fetch số lượng giỏ hàng", error);
    }
  };

  const handleSearch = () => {
    if (keyword.trim()) {
      navigate(`/searchProduct/${encodeURIComponent(keyword)}`);
    }
  };

  const goToCart = () => {
    navigate("/cart/items");
  };

  const handleLogout = async () => {
    try {
      const res = await fetchWithAuth("/api/logout", {
        method: "POST",
      });

      if (res.ok) {
        localStorage.removeItem("customer");
        setCustomer(null);
        navigate("/");
      }
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  return (
    <header className="sticky">
      <div className="container">

        <div className="logo">
          <NavLink
            to="/"
            className={({ isActive }) =>
              `logo-link ${isActive ? "active" : ""}`
            }
          >
            PavuShop
          </NavLink>
        </div>

        <nav className="navbar">

          <div className="nav-left">

            <NavLink
              to="/"
              end
              className={({ isActive }) =>
                `nav-link bold ${isActive ? "active" : ""}`
              }
            >
              Home
            </NavLink>

            <NavLink
              to="/products"
              className={({ isActive }) =>
                `nav-link bold ${isActive ? "active" : ""}`
              }
            >
              Products
            </NavLink>

          </div>

          <div className="search-box">
            <Input
              prefix={<SearchOutlined />}
              placeholder="Search Shop"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              onPressEnter={handleSearch}
              style={{ width: "250px" }}
            />
          </div>

          <div
            className="cart-icon"
            onClick={goToCart}
            style={{ cursor: "pointer" }}
          >
            <ShoppingCartOutlined className="nav-icon" />
            <span> ({cartCount})</span>
          </div>

          {!customer ? (
            <div className="nav-right">

              <NavLink
                to="/register"
                className={({ isActive }) =>
                  `nav-link bold ${isActive ? "active" : ""}`
                }
              >
                Register
              </NavLink>

              <NavLink
                to="/login"
                className={({ isActive }) =>
                  `nav-link bold ${isActive ? "active" : ""}`
                }
              >
                Login
              </NavLink>

            </div>
          ) : (
            <div className="nav-right">

              <span className="nav-link">
                Xin chào, <b>{customer.username}</b>
              </span>

              <button
                onClick={handleLogout}
                className="nav-link bold logout-button"
              >
                Logout
              </button>

            </div>
          )}

        </nav>
      </div>
    </header>
  );
};

export default Header;