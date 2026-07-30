import React, { useState, useEffect } from 'react';
import { Layout, Menu } from 'antd';
import {
  AppstoreOutlined, BarChartOutlined, TeamOutlined, ShoppingOutlined,
  TagsOutlined, FolderOpenOutlined, LineChartOutlined,
} from '@ant-design/icons';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import AdminHeader from '@/admin/components/Header';

const { Header, Sider, Content } = Layout;

const Home = () => {
  const [selectedKey, setSelectedKey] = useState('');
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const path = location.pathname.replace('/admin/', '');
    setSelectedKey(path);
  }, [location]);

  const handleMenuClick = ({ key }) => {
    setSelectedKey(key);
    navigate(`/admin/${key}`);
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>

      <Header style={{ background: '#001529', padding: 0 }}>
        <AdminHeader />
      </Header>

      <Layout>

        <Sider width={250} className="site-layout-background">
          <Menu
            mode="inline"
            selectedKeys={[selectedKey]}
            onClick={handleMenuClick}
            style={{ height: '100%', borderRight: 0 }}
          >
            <Menu.ItemGroup key="system" title="🔧 Quản lý hệ thống">
              <Menu.Item key="category" icon={<FolderOpenOutlined />}>
                QL Thể loại
              </Menu.Item>
              <Menu.Item key="product" icon={<ShoppingOutlined />}>
                QL Sản phẩm
              </Menu.Item>
              <Menu.Item key="brand" icon={<TagsOutlined />}>
                QL Nhãn hiệu
              </Menu.Item>
              <Menu.Item key="order" icon={<BarChartOutlined />}>
                QL Đơn hàng
              </Menu.Item>
              <Menu.Item key="customer" icon={<TeamOutlined />}>
                QL Khách hàng
              </Menu.Item>
            </Menu.ItemGroup>

            <Menu.ItemGroup key="revenue" title="💰 Quản lý doanh thu">
              <Menu.Item key="stat-product" icon={<AppstoreOutlined />}>
                TK Sản phẩm
              </Menu.Item>
              <Menu.Item key="stat-category" icon={<FolderOpenOutlined />}>
                TK Thể loại
              </Menu.Item>
              <Menu.Item key="stat-brand" icon={<TagsOutlined />}>
                TK Nhãn hiệu
              </Menu.Item>
              <Menu.Item key="stat-year" icon={<LineChartOutlined />}>
                TK Theo năm
              </Menu.Item>
              <Menu.Item key="stat-quarter" icon={<LineChartOutlined />}>
                TK Theo quý
              </Menu.Item>
              <Menu.Item key="stat-customer" icon={<TeamOutlined />}>
                TK Theo khách hàng
              </Menu.Item>
            </Menu.ItemGroup>
          </Menu>
        </Sider>

        <Layout style={{ padding: '24px', background: '#f0f2f5' }}>
          <Content
            style={{
              padding: 24,
              margin: 0,
              minHeight: 280,
              background: '#fff',
            }}
          >
            <Outlet />
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
};

export default Home;
