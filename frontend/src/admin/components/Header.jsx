import React, { useContext } from 'react';
import { Layout, Button, message } from 'antd';
import { AuthContext } from '@/context/AuthContext';
import fetchWithAuth from '@/services/fetchWithAuth';
import { useNavigate } from 'react-router-dom'; 
const { Header: AntHeader } = Layout;

const AdminHeader = () => {

  const { customer, setCustomer } = useContext(AuthContext);
  const navigate = useNavigate();
  const handleLogout = async () => {
    try {
      const res = await fetchWithAuth('/api/logout', {
        method: 'POST'
      });

      if (res.ok) {
        localStorage.removeItem("customer"); 
        setCustomer(null); 
        message.success('Đăng xuất thành công!');
        navigate('/login'); 
      } else {
        message.error('Đăng xuất thất bại');
      }
    } catch (error) {
      message.error('Lỗi khi đăng xuất');
    }
  };

  return (
    <AntHeader
      style={{
        background: '#001529',
        padding: '0 24px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
      }}
    >
      <div style={{ color: '#fff', fontSize: 20, fontWeight: 600 }}>
        🛠️ Admin
      </div>
      <div style={{ color: '#fff', fontSize: 16, display: 'flex', alignItems: 'center' }}>
        <span style={{ marginRight: 16 }}>
          Xin chào, <span style={{ fontWeight: 600 }}>{customer ? customer.username : '...'}</span>
        </span>
        <Button type="link" onClick={handleLogout} style={{ color: '#fff' }}>
          Đăng xuất
        </Button>
      </div>
    </AntHeader>
  );
};

export default AdminHeader;
