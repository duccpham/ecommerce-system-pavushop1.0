package com.shop.pavushop.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	private String name;
	private String image;
	private double price;
	private int quantity;
	private double discount;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dddd")
	private Date enteredDate;
	@Column(length = 2000)
	private String description;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "categoryId")
	private Category category;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "brandId")
	private Brand brand;

	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private Collection<OrderDetail> orderDetails;
}
