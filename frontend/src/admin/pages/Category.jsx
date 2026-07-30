import React, { useEffect, useState } from 'react';
import {
  Table, Button, Space, Popconfirm, message, Modal, Form, Input,
} from 'antd';
import fetchWithAuth from '@/services/fetchWithAuth';

const Category = () => {
  const [categories, setCategories] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [form] = Form.useForm();
  const [isEditMode, setIsEditMode] = useState(false);
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const res = await fetchWithAuth('/api/admin/categories');
      const data = await res.json();
      setCategories(data);
    } catch (error) {
      console.error('Lỗi khi load categories:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      const res = await fetchWithAuth(`/api/admin/categories/delete/${id}`, {
        method: 'DELETE'
      });
      fetchCategories();
    } catch (error) {
      message.error('Xóa thất bại!');
    }
  };

  const handleEdit = async (id) => {
    try {
      const res = await fetchWithAuth(`/api/admin/categories/edit/${id}`);
      if (res.ok) {
        const data = await res.json();
        form.setFieldsValue({ categoryName: data.categoryName });
        setEditingId(id);
        setIsEditMode(true);
        setIsModalVisible(true);
      } else {
        message.error('Không tìm thấy thể loại cần sửa');
      }
    } catch (error) {
      console.error('Lỗi khi lấy thông tin thể loại:', error);
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
        const res = await fetchWithAuth(`/api/admin/categories/update/${editingId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(values),
        });

        if (res.ok) {
          message.success('Cập nhật thể loại thành công!');
          fetchCategories();
          setIsModalVisible(false);
        } else {
          message.error('Cập nhật thất bại!');
        }
      } else {
        const res = await fetchWithAuth('/api/admin/categories/add', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(values),
        });

        if (res.ok) {
          message.success('Thêm thể loại thành công!');
          fetchCategories();
          setIsModalVisible(false);
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
    { title: 'ID', dataIndex: 'categoryId', key: 'categoryId', },
    { title: 'Tên thể loại', dataIndex: 'categoryName', key: 'categoryName', },
    {
      title: 'Hành động',
      key: 'action',
      render: (_, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record.categoryId)}>
            Sửa
          </Button>
          <Popconfirm
            title="Bạn có chắc chắn muốn xóa?"
            onConfirm={() => handleDelete(record.categoryId)}
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
      <Table columns={columns} dataSource={categories} rowKey="categoryId" />

      <Modal
        title={isEditMode ? 'Sửa thể loại' : 'Thêm thể loại mới'}
        open={isModalVisible}
        onOk={handleModalOk}
        onCancel={handleModalCancel}
        okText="Lưu"
        cancelText="Hủy"
      >
        <Form form={form} layout="vertical" name="formCategory">
          <Form.Item
            label="Tên thể loại"
            name="categoryName"
            rules={[{ required: true, message: 'Vui lòng nhập tên thể loại!' }]}
          >
            <Input placeholder="Nhập tên thể loại" />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default Category;
