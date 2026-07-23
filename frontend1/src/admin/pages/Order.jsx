import React, { useEffect, useState } from 'react';
import {
  Table, Button, Space, message, Modal, Form, Input, Select,
} from 'antd';
import fetchWithAuth from '@/services/fetchWithAuth';
const { Option } = Select;


const Order = () => {
  const [orders, setOrders] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [editingOrder, setEditingOrder] = useState(null);
  const [form] = Form.useForm();

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const res = await fetchWithAuth('/api/admin/orders');
      const data = await res.json();
      setOrders(data);
    } catch (error) {
      console.error('Lỗi khi load orders:', error);
    }
  };

  const handleEdit = async (id) => {
    try {
      const res = await fetchWithAuth(`/api/admin/orders/editorder/${id}`);
      const data = await res.json();
      form.setFieldsValue({
        status: data.status,
        description: data.description,
        receiver: data.receiver,
        address: data.address,
        phone: data.phone,
        totalPrice: data.totalPrice,
      });
      setEditingId(id);
      setEditingOrder(data);
      setIsModalVisible(true);
    } catch (error) {
      message.error('Không thể tải dữ liệu để sửa');
    }
  };

  const handleModalOk = async () => {
    try {
      const values = await form.validateFields();

      const updatedOrder = {
        ...editingOrder,
        ...values,
      };

      const res = await fetchWithAuth(`/api/admin/orders/update/${editingId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedOrder),
      });

      if (res.ok) {
        message.success('Cập nhật đơn hàng thành công!');
        setIsModalVisible(false);
        setEditingOrder(null);
        fetchOrders();
      } else {
        message.error('Cập nhật thất bại!');
      }
    } catch (err) {
      console.log('Validate Failed:', err);
    }
  };

  const handleModalCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
    setEditingId(null);
    setEditingOrder(null);
  };

  const columns = [
    { title: 'ID', dataIndex: 'orderId', key: 'orderId', },
    { title: 'Tên khách hàng', dataIndex: 'receiver', key: 'receiver', },
    { title: 'Địa chỉ', dataIndex: 'address', key: 'address', },
    { title: 'Số điện thoại', dataIndex: 'phone', key: 'phone', },
    { title: 'Ngày đặt hàng', dataIndex: 'orderDate', key: 'orderDate', },
    { title: 'Tổng tiền', dataIndex: 'totalPrice', key: 'totalPrice', },
    {
      title: 'Trạng thái',
      dataIndex: 'status',
      key: 'status',
      render: (status) => {
        let color = status === 'Đã thanh toán' ? 'green' : 'orange';
        return <span style={{ color }}>{status}</span>;
      },
    },
    {
      title: 'Hành động',
      key: 'action',
      render: (_, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record.orderId)}>
            Sửa
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Table columns={columns} dataSource={orders} rowKey="orderId" />

      <Modal
        title="Sửa đơn hàng"
        open={isModalVisible}
        onOk={handleModalOk}
        onCancel={handleModalCancel}
        okText="Lưu"
        cancelText="Hủy"
      >
        <Form form={form} layout="vertical">
          <Form.Item
            label="Trạng thái"
            name="status"
            rules={[{ required: true, message: 'Vui lòng chọn trạng thái!' }]}
          >
            <Select>
              <Option value="Đang giao dịch">Đang giao dịch</Option>
              <Option value="Đã thanh toán">Đã thanh toán</Option>
            </Select>
          </Form.Item>

          <Form.Item label="Mô tả" name="description">
            <Input disabled />
          </Form.Item>
          <Form.Item label="Người nhận" name="receiver">
            <Input disabled />
          </Form.Item>
          <Form.Item label="Địa chỉ" name="address">
            <Input disabled />
          </Form.Item>
          <Form.Item label="Số điện thoại" name="phone">
            <Input disabled />
          </Form.Item>
          <Form.Item label="Tổng tiền" name="totalPrice">
            <Input disabled />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default Order;
