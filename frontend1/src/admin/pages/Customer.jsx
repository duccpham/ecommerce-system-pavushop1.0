import React, { useEffect, useState } from 'react';
import { Table, Tag } from 'antd';
import fetchWithAuth from '@/services/fetchWithAuth';

const Customer = () => {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    fetchCustomers();
  }, []);

  const fetchCustomers = async () => {
    try {
      const res = await fetchWithAuth('/api/admin/customer')
      const data = await res.json();
      setCustomers(data);
    } catch (error) {
      console.error('Lỗi khi load customers:', error);
    }
  };

  const columns = [
    { title: 'ID', dataIndex: 'id', key: 'id', },
    { title: 'Tên đăng nhập', dataIndex: 'username', key: 'username', },
    { title: 'Họ tên', dataIndex: 'fullname', key: 'fullname', },
    { title: 'Email', dataIndex: 'email', key: 'email', },
    {
      title: 'Role',
      dataIndex: 'roles',
      key: 'roles',
      render: (roles) =>
        roles.map(role => (
          <Tag key={role.id} color="blue">
            {role.name}
          </Tag>
        )),
    }
  ];

  return (
    <div>
      <h2>Quản lý Khách hàng</h2>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={customers}
        pagination={{ pageSize: 6 }}
      />
    </div>
  );
};

export default Customer;
