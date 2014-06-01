package main.java.info.jtrac.service.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.jtrackinventory.business.bean.Material;
import be.jtrackinventory.business.bean.MaterialComponent;
import be.jtrackinventory.business.bean.MaterialComponentObject;
import be.jtrackinventory.business.bean.MaterialObject;
import be.jtrackinventory.business.bean.Property;
import be.jtrackinventory.business.bean.PropertyGroup;
import be.jtrackinventory.business.bean.PropertyType;
import be.jtrackinventory.business.bean.Value;
import be.jtrackinventory.business.data.IPersistenceDAOImpl;
import be.jtrackinventory.exception.data.DataDAOException;
@SuppressWarnings("unchecked")
public class MaterialObjectDAOImplTest {
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IPersistenceDAOImpl<MaterialObject> materialObjectDAOImpl = (IPersistenceDAOImpl<MaterialObject>) context.getBean("materialObjectDAOImpl");
	IPersistenceDAOImpl<Material> materialDAOImpl = (IPersistenceDAOImpl<Material>) context.getBean("materialDAOImpl");
	IPersistenceDAOImpl<Property> propertyDAOImpl = (IPersistenceDAOImpl<Property>) context.getBean("propertyDAOImpl");
	IPersistenceDAOImpl<PropertyType> propertyTypeDAOImpl = (IPersistenceDAOImpl<PropertyType>) context.getBean("propertyTypeDAOImpl");
	IPersistenceDAOImpl<PropertyGroup> propertyGroupDAOImpl = (IPersistenceDAOImpl<PropertyGroup>) context.getBean("propertyGroupDAOImpl");
	IPersistenceDAOImpl<Value> valueDAOImpl = (IPersistenceDAOImpl<Value>) context.getBean("valueDAOImpl");
	IPersistenceDAOImpl<MaterialComponent> materialComponentDAOImpl = (IPersistenceDAOImpl<MaterialComponent>) context.getBean("materialComponentDAOImpl");
	IPersistenceDAOImpl<MaterialComponentObject> materialComponentObjectDAOImpl = (IPersistenceDAOImpl<MaterialComponentObject>) context.getBean("materialComponentObjectDAOImpl");
	/* PropertyType */
	private PropertyType propertyType;
	/* Property */
	private Property pr_SerialNumber;
	private Property pr_Description;
	/* PropertyGroup */
	private PropertyGroup prg_General;
	/* Material */
	private Material mat_Laptop;
	/**
	 * Method will initialize the objects used
	 */
	@Before
	public void before(){
		/* PropertyType */
		this.propertyType = new PropertyType("Text","TextField");
		/* Property */
		this.pr_SerialNumber = new Property("SerialNumber","Serialnumber object");
		this.pr_Description = new Property("Description","Object description");
		this.pr_SerialNumber.setPropertyType(this.propertyType);
		this.pr_Description.setPropertyType(this.propertyType);
		/* PropertyGroup */
		this.prg_General = new PropertyGroup("General","General Data");
		/* Material */
		this.mat_Laptop = new Material("Laptop","ClientLaptop");
		/* persist */
		try {
			this.propertyTypeDAOImpl.persist(this.propertyType);
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		try {
			this.propertyDAOImpl.persist(this.pr_SerialNumber);
			this.propertyDAOImpl.persist(this.pr_Description);
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		try {
			this.propertyGroupDAOImpl.persist(this.prg_General);
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		try {
			this.materialDAOImpl.persist(this.mat_Laptop);
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		/* update */
		this.pr_Description.setPropertyGroup(this.prg_General);
		this.pr_SerialNumber.setPropertyGroup(this.prg_General);
		try {
			this.propertyDAOImpl.update(this.pr_SerialNumber);
			this.propertyDAOImpl.update(this.pr_Description);
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		this.mat_Laptop.setPropertyGroup(prg_General);
		try {
			this.materialDAOImpl.update(this.mat_Laptop);
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
	}
	/*
	 * scenario01 :
	 * 01 - create a MaterialObject
	 * 02 - set the Material
	 * 03 - persist the object in the database
	 * 04 - delete the object in the database
	 */
	@Test
	public void scenario01() {
		/* 01 - create a MaterialObject */
		MaterialObject matObject = new MaterialObject();
		/* 02 - set the Material */
		matObject.setMaterial(this.mat_Laptop);
		/* 03 - persist the object in the database */
		try {
			this.materialObjectDAOImpl.persist(matObject);
			/* Check the Object in the database */
			MaterialObject matObjectFound = this.materialObjectDAOImpl.findByID(matObject.getUniqueId());
			assertNotNull(matObjectFound);
			assertNotNull(this.mat_Laptop);
			assertEquals(this.mat_Laptop, matObject.getMaterial());
			assertEquals("Laptop", matObject.getMaterial().getCaption());
			assertEquals("ClientLaptop", matObject.getMaterial().getDescription());
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
//		/* 04 - delete the object in the database */
//		try{
//			this.materialObjectDAOImpl.delete(matObject.getUniqueId());
//		} catch (DataDAOException dXe) {
//			assertEquals("", dXe.getMessage());
//		}
	}
	/*
	 * scenario 02 :
	 * 01 - create a MaterialObject
	 * 02 - set the Material
	 * 03 - persist the object in the database
	 * 04 - create Values
	 * 05 - add values to the object
	 * 06 - persist the MaterialObject in the database
	 * 07 - remove a value
	 * 08 - update the MaterialObject in the database
	 * 09 - add the MaterialComponentObject to the MaterialObject
	 * 10 - update the MaterialObject in the database
	 * 09 - delete the Values
	 */
	@Test
	public void scenario02() {
		/* 01 - create a MaterialObject */
		MaterialObject matObject = new MaterialObject();
		/* 02 - set the Material */
		matObject.setMaterial(this.mat_Laptop);
		/* 03 - persist the object in the database */
		try {
			this.materialObjectDAOImpl.persist(matObject);
			/* Check the Object in the database */
			MaterialObject matObjectFound = this.materialObjectDAOImpl.findByID(matObject.getUniqueId());
			assertNotNull(matObjectFound);
			assertNotNull(this.mat_Laptop);
			assertEquals(this.mat_Laptop, matObject.getMaterial());
			assertEquals("Laptop", matObject.getMaterial().getCaption());
			assertEquals("ClientLaptop", matObject.getMaterial().getDescription());
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		/* 04 - create Values */
		Value vl_SerialNumber = new Value(this.pr_SerialNumber);
		Value vl_Description = new Value(this.pr_Description);
		/* 05 - add values to the MaterialObject */
		matObject.getValues().add(vl_SerialNumber);
		matObject.getValues().add(vl_Description);
		/* persist the values in the database */
		try {
			this.valueDAOImpl.persist(vl_SerialNumber);
			this.valueDAOImpl.persist(vl_Description);
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		/* 06 - persist the MaterialObject in the database */
		try {
			this.materialObjectDAOImpl.update(matObject);
			/* Check the Object in the database */
			MaterialObject matObjectFound = this.materialObjectDAOImpl.findByID(matObject.getUniqueId());
			assertNotNull(matObjectFound);
			assertNotNull(this.mat_Laptop);
			assertEquals(this.mat_Laptop, matObject.getMaterial());
			assertEquals("Laptop", matObject.getMaterial().getCaption());
			assertEquals("ClientLaptop", matObject.getMaterial().getDescription());
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
		/* 07 - remove a value */
		matObject.getValues().remove(vl_Description);
		/* 08 - update the MaterialObject in the database */
		try {
			this.materialObjectDAOImpl.update(matObject);
			/* Check the Object in the database */
			MaterialObject matObjectFound = this.materialObjectDAOImpl.findByID(matObject.getUniqueId());
			assertNotNull(matObjectFound);
			assertNotNull(this.mat_Laptop);
			assertEquals(this.mat_Laptop, matObject.getMaterial());
			assertEquals("Laptop", matObject.getMaterial().getCaption());
			assertEquals("ClientLaptop", matObject.getMaterial().getDescription());
			assertNotNull(matObjectFound.getValues());
			assertEquals(1, matObjectFound.getValues().size());
			assertEquals(vl_SerialNumber.getUniqueId(), matObjectFound.getValues().get(0).getUniqueId());
		} catch (DataDAOException dXe) {
			assertEquals("", dXe.getMessage());
		}
//		/* 11 - delete the Values */
//		try{
//			this.valueDAOImpl.delete(vl_SerialNumber.getUniqueId());
//			this.valueDAOImpl.delete(vl_Description.getUniqueId());
//			this.materialObjectDAOImpl.delete(matObject.getUniqueId());
//		} catch (DataDAOException dXe) {
//			assertEquals("", dXe.getMessage());
//		}
	}
	/**
	 * Method will delete the object in the database
	 */
	@After
	public void after(){
//		/* delete Material */
//		try {
//			this.materialDAOImpl.delete(this.mat_Laptop.getUniqueId());
//		} catch (DataDAOException dXe) {
//			assertEquals("", dXe.getMessage());
//		}
//		/* delete Property */
//		try {
//			this.propertyDAOImpl.delete(this.pr_SerialNumber.getUniqueId());
//			this.propertyDAOImpl.delete(this.pr_Description.getUniqueId());
//		} catch (DataDAOException dXe) {
//			assertEquals("", dXe.getMessage());
//		}
//		/* delete PropertyType */
//		try {
//			this.propertyTypeDAOImpl.delete(this.propertyType.getUniqueId());
//		} catch (DataDAOException dXe) {
//			assertEquals("", dXe.getMessage());
//		}
//		try {
//			this.propertyGroupDAOImpl.delete(this.prg_General.getUniqueId());
//		} catch (DataDAOException dXe) {
//			assertEquals("", dXe.getMessage());
//		}
	}

}
