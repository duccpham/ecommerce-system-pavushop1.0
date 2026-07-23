import React, { useEffect, useState } from 'react';
import {
  Table, Button, Space, Popconfirm, message, Modal, Form, Input,
} from 'antd';
import fetchWithAuth from '@/services/fetchWithAuth';


const Brand = () => {
  const [brands, setBrands] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [form] = Form.useForm();

  useEffect(() => {
    fetchBrands();
  }, []);

  const fetchBrands = async () => {
    try {
      const res = await fetchWithAuth('/api/admin/brands');
      const data = await res.json();
      setBrands(data);
    } catch (error) {
      console.error('Lỗi khi load brands:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      const res = await fetchWithAuth(`/api/admin/brands/delete/${id}`, {
        method: 'DELETE',
      });
      fetchBrands();
    } catch (error) {
      message.error('Xóa thất bại!');
    }
  };

  const handleEdit = async (id) => {
    try {
      const res = await fetchWithAuth(`/api/admin/brands/edit/${id}`);
      const data = await res.json();
      form.setFieldsValue({ brandName: data.brandName });
      setEditingId(id);
      setIsEditMode(true);
      setIsModalVisible(true);
    } catch (error) {
      message.error('Không thể tải dữ liệu để sửa');
    }
  };

  const handleAddNew = () => {
    form.resetFields();
    setIsEditMode(false);
    setIsModalVisible(true);
  };

  const handleModalOk = async () => {
    try {
      const values = await form.validateFields();

      if (isEditMode) {
        const res = await fetchWithAuth(`/api/admin/brands/update/${editingId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(values),
        });

        if (res.ok) {
          message.success('Cập nhật thương hiệu thành công!');
          setIsModalVisible(false);
          fetchBrands();
        } else {
          message.error('Cập nhật thất bại!');
        }
      } else {
        const res = await fetchWithAuth('/api/admin/brands/add', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(values),
        });

        if (res.ok) {
          message.success('Thêm thương hiệu thành công!');
          setIsModalVisible(false);
          fetchBrands();
        } else {
          message.error('Thêm thất bại!');
        }
      }
    } catch (err) {
      console.log('Validate Failed:', err);
    }
  };

  const handleModalCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
    setEditingId(null);
    setIsEditMode(false);
  };

  const columns = [
    { title: 'ID', dataIndex: 'brandId', key: 'brandId', },
    { title: 'Tên thương hiệu', dataIndex: 'brandName', key: 'brandName', },
    {
      title: 'Hành động',
      key: 'action',
      render: (_, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record.brandId)}>
            Sửa
          </Button>
          <Popconfirm
            title="Bạn có chắc chắn muốn xóa?"
            onConfirm={() => handleDelete(record.brandId)}
            okText="Xóa"
            cancelText="Hủy"
          >
            <Button type="link" danger>
              Xóa
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAddNew} style={{ marginBottom: 16 }}>
        ➕ Thêm mới
      </Button>
      <Table columns={columns} dataSource={brands} rowKey="brandId" />

      <Modal
        title={isEditMode ? 'Sửa thương hiệu' : 'Thêm thương hiệu mới'}
        open={isModalVisible}
        onOk={handleModalOk}
        onCancel={handleModalCancel}
        okText="Lưu"
        cancelText="Hủy"
      >
        <Form form={form} layout="vertical" name="formBrand">
          <Form.Item
            label="Tên thương hiệu"
            name="brandName"
            rules={[{ required: true, message: 'Vui lòng nhập tên thương hiệu!' }]}
          >
            <Input placeholder="Nhập tên thương hiệu" />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default Brand;
