import React, { useEffect, useState } from 'react';
import { Table, Button, Modal, Form, Input, InputNumber, Select, message, Popconfirm } from 'antd';
import fetchWithAuth from '@/services/fetchWithAuth';
const { Option } = Select;

const Product = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [brands, setBrands] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isEdit, setIsEdit] = useState(false);
  const [editingProduct, setEditingProduct] = useState(null);
  const [form] = Form.useForm();

  useEffect(() => {
    fetchProducts();
    fetchCategories();
    fetchBrands();
  }, []);

  const fetchProducts = async () => {
    try {
      const res = await fetchWithAuth('/api/admin/products');
      const data = await res.json();
      setProducts(data);
    } catch (error) {
      console.error('Lỗi khi load products:', error);
    };
  }
  const fetchCategories = async () => {
    try {
      const res = await fetchWithAuth('/api/admin/categories');
      const data = await res.json();
      setCategories(data);
    } catch (error) {
      console.error('Lỗi khi load categoryes:', error);
    };
  };

  const fetchBrands = async () => {
    try {
      const res = await fetchWithAuth('/api/admin/brands');
      const data = await res.json();
      setBrands(data);
    } catch (error) {
      console.error('Lỗi khi load brands:', error);
    };
  };

  const showModal = (record = null) => {
    if (record) {
      setIsEdit(true);
      setEditingProduct(record);
      form.setFieldsValue({
        name: record.name,
        price: record.price,
        discount: record.discount,
        quantity: record.quantity,
        description: record.description,
        category: record.category.categoryId,
        brand: record.brand.brandId,
      });
    } else {
      setIsEdit(false);
      setEditingProduct(null);
      form.resetFields();
    }
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
    setEditingProduct(null);
    setIsEdit(false);
  };

  const handleModalOk = async () => {
    try {
      const values = await form.validateFields();

      let imageName = "";

      if (values.image) {
        const formData = new FormData();
        formData.append("image", values.image);

        const res = await fetchWithAuth("http://localhost:8080/images/upload", {
          method: "POST",
          body: formData,
        });

        if (!res.ok) {
          message.error("Tải ảnh lên thất bại!");
          return;
        }

        imageName = await res.text();
      }



      const productData = {
        name: values.name,
        price: values.price,
        discount: values.discount,
        quantity: values.quantity,
        description: values.description,
        categoryId: values.category,
        brandId: values.brand,
        //“Nếu có ảnh mới (imageName) thì dùng nếu không có thì:đang sửa → dùng ảnh cũ đang thêm → để trống”
        imageURL: imageName || (isEdit ? editingProduct.imageURL : '')
      };

      const url = isEdit ? `/api/admin/products/update/${editingProduct.productId}` : '/api/admin/products/add';
      const method = isEdit ? 'PUT' : 'POST';

      const res = await fetchWithAuth(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(productData),
      });

      if (res.ok) {
        message.success(`${isEdit ? 'Cập nhật' : 'Thêm'} sản phẩm thành công!`);
        setIsModalVisible(false);
        form.resetFields();
        fetchProducts();
      } else {
        message.error(`${isEdit ? 'Cập nhật' : 'Thêm'} sản phẩm thất bại!`);
      }
    } catch (err) {
      console.log('Lỗi validate:', err);
    }
  };

  const handleDelete = async (id) => {
    try {
      const res = await fetchWithAuth(`/api/admin/products/delete/${id}`, {
        method: 'DELETE',
      });
      if (res.ok) {
        message.success('Xóa sản phẩm thành công!');
        fetchProducts();
      } else {
        message.error('Xóa sản phẩm thất bại!');
      }
    } catch (error) {
      console.error('Lỗi khi xóa sản phẩm:', error);
      message.error('Có lỗi xảy ra khi xóa sản phẩm.');
    }
  };

  const columns = [
    { title: 'ID', dataIndex: 'productId', key: 'id', },
    { title: 'Tên', dataIndex: 'name', key: 'name', },
    {
      title: 'Hình ảnh',
      dataIndex: 'image',
      key: 'image',
      render: (image) =>
        image ? (
          <img
            src={`http://localhost:8080/images/${image}`}
            alt="product"
            style={{ width: 60, height: 60, objectFit: 'cover' }}
          />
        ) : (
          'Không có ảnh'
        ),
    },
    { title: 'Thể loại', dataIndex: ['category', 'categoryName'], key: 'category', },
    { title: 'Thương hiệu', dataIndex: ['brand', 'brandName'], key: 'brand', },
    { title: 'Giá', dataIndex: 'price', key: 'price', },
    { title: 'Giảm giá', dataIndex: 'discount', key: 'discount', },
    { title: 'Số lượng', dataIndex: 'quantity', key: 'quantity', },
    { title: 'Mô tả', dataIndex: 'description', key: 'description', },
    {
      title: 'Hành động',
      key: 'action',
      render: (_, record) => (
        <>
          <Button type='link' onClick={() => showModal(record)}>
            Sửa
          </Button>
          <Popconfirm
            title="Bạn có chắc chắn muốn xóa sản phẩm này?"
            onConfirm={() => handleDelete(record.productId)}
            okText="Xóa"
            cancelText="Hủy"
          >
            <Button type='link' danger>Xóa</Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={() => showModal()} style={{ marginBottom: 16 }}>
        Thêm sản phẩm
      </Button>
      <Table dataSource={products} columns={columns} rowKey="productId" />

      <Modal
        title={isEdit ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm mới'}
        open={isModalVisible}
        onCancel={handleCancel}
        onOk={handleModalOk}
      >
        <Form form={form} layout="vertical">
          <Form.Item label="Tên sản phẩm" name="name" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item label="Giá" name="price" rules={[{ required: true }]}>
            <InputNumber min={0} style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item label="Giảm giá (%)" name="discount" rules={[{ required: true }]}>
            <InputNumber min={0} max={100} style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item label="Số lượng" name="quantity" rules={[{ required: true }]}>
            <InputNumber min={0} style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item label="Mô tả" name="description" rules={[{ required: true }]}>
            <Input.TextArea />
          </Form.Item>
          <Form.Item label="Thể loại" name="category" rules={[{ required: true }]}>
            <Select>
              {categories.map((cat) => (
                <Option key={cat.categoryId} value={cat.categoryId}>
                  {cat.categoryName}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item label="Thương hiệu" name="brand" rules={[{ required: true }]}>
            <Select>
              {brands.map((brand) => (
                <Option key={brand.brandId} value={brand.brandId}>
                  {brand.brandName}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            label="Hình ảnh"
            name="image"
            getValueFromEvent={(e) => e?.target?.files?.[0]}
          >
            <input type="file" accept="image/png, image/jpeg, image/jpg" />
          </Form.Item>
          {isEdit && editingProduct?.image && (
            <div style={{ marginBottom: 16 }}>
              <span>Ảnh hiện tại:</span>
              <br />
              <img
                src={`http://localhost:8080/images/${editingProduct.image}`}
                alt="current"
                style={{ width: 100, height: 100, objectFit: 'cover', marginTop: 8 }}
              />
            </div>
          )}
        </Form>
      </Modal>
    </div>
  );
};

export default Product;
