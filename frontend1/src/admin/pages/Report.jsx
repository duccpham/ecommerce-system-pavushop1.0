import React, { useEffect, useState } from 'react';
import { Table, Spin } from 'antd';
import { useLocation } from 'react-router-dom';
import fetchWithAuth from '@/services/fetchWithAuth';

const apiMap = {
  "stat-product": '/api/admin/report/products',
  "stat-category": '/api/admin/report/Category',
  "stat-brand": '/api/admin/report/Brands',
  "stat-year": '/api/admin/report/Year',
  "stat-quarter": '/api/admin/report/Quarter',
  "stat-customer": '/api/admin/report/OrderCustomer'
};

const Report = () => {
  const location = useLocation();
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const getApiUrl = () => {
    const path = location.pathname.replace('/admin/', '');
    return apiMap[path] || '';
  };

  useEffect(() => {
    fetchReport();
  }, [location.pathname]);


  const fetchReport = async () => {
    const apiUrl = getApiUrl();
    if (!apiUrl) return;

    setLoading(true);
    try {
      const res = await fetchWithAuth(apiUrl);
      const rawData = await res.json();

      const formatted = rawData.map((item, index) => ({
        key: index,
        name: item[0],
        quantity: item[1],
        revenue: item[2],
        averagePrice: item[3],
        minPrice: item[4],
        maxPrice: item[5],
      }));
      setData(formatted);
    } catch (error) {
      console.error('Lỗi khi fetch dữ liệu thống kê:', error);
    } finally {
      setLoading(false);
    }
  };

  const columnNames = [
    { title: 'Tên', dataIndex: 'name', key: 'name' },
    { title: 'Số lượng', dataIndex: 'quantity', key: 'quantity' },
    { title: 'Doanh thu', dataIndex: 'revenue', key: 'revenue' },
    { title: 'Giá trung bình', dataIndex: 'averagePrice', key: 'averagePrice' },
    { title: 'Giá thấp nhất', dataIndex: 'minPrice', key: 'minPrice' },
    { title: 'Giá cao nhất', dataIndex: 'maxPrice', key: 'maxPrice' },
  ];

  return (
    <div>
      {loading ? <Spin /> : <Table columns={columnNames} dataSource={data} pagination={false} />}
    </div>
  );
};

export default Report;
