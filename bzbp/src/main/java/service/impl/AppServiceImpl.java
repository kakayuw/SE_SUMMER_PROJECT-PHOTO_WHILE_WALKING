package service.impl;

import java.util.List;

import model.Book;
import model.Bookdetail;
import model.Order;
import model.Orderitem;
import model.User;
import service.AppService;
import dao.BookDao;
import dao.BookdetailDao;
import dao.LoginDao;
import dao.OrderDao;
import dao.OrderitemDao;
import dao.UserDao;
import dao.impl.LoginDaoImpl;

/**
 * @author seniyuting
 * @version 1.0
 * 
 */
public class AppServiceImpl implements AppService {

	private BookDao bookDao;
	private BookdetailDao bookdetailDao;
	private OrderDao orderDao;
	private OrderitemDao orderitemDao;
	private UserDao userDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	
	public BookdetailDao getBookdetailDao() {
		return bookdetailDao;
	}
	
	public void setBookdetailDao(BookdetailDao bookdetailDao) {
		this.bookdetailDao = bookdetailDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setOrderitemDao(OrderitemDao orderitemDao) {
		this.orderitemDao = orderitemDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * book
	 * 
	 */
	public Integer addBook(Book book) {
		return bookDao.save(book);
	}
	
	public void addBookdetail(Bookdetail bookdetail){
		bookdetailDao.saveOrUpdate(bookdetail);
	}
	
	public void addBookpicture(int id, String source){
		bookdetailDao.savePicture(id, source);
	}

	public void deleteBook(Book book) {
		bookDao.delete(book);
	}

	public void updateBook(Book book) {
		bookDao.update(book);
	}

	public Book getBookById(int id) {
		return bookDao.getBookById(id);
	}
	
	public Bookdetail getBookdetailById(int id) {
		return bookdetailDao.findById(id);
	}

	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}

	/**
	 * order
	 * 
	 */
	public Integer addOrder(Order order) {
		return orderDao.save(order);
	}

	public void deleteOrder(Order order) {
		orderDao.delete(order);
	}

	public void updateOrder(Order order) {
		orderDao.update(order);
	}

	public Order getOrderById(int id) {
		return orderDao.getOrderById(id);
	}

	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}

	/**
	 * order item
	 * 
	 */
	public Integer addOrderitem(Orderitem orderitem) {
		return orderitemDao.save(orderitem);
	}

	public void deleteOrderitem(Orderitem orderitem) {
		orderitemDao.delete(orderitem);
	}

	public void updateOrderitem(Orderitem orderitem) {
		orderitemDao.update(orderitem);
	}

	public Orderitem getOrderitemById(int id) {
		return orderitemDao.getOrderitemById(id);
	}

	public List<Orderitem> getAllOrderitems() {
		return orderitemDao.getAllOrderitems();
	}

	/**
	 * user
	 * 
	 */
	public Integer addUser(User user) {
		return userDao.save(user);
	}

	public void deleteUser(User user) {
		userDao.delete(user);
	}

	public void updateUser(User user) {
		userDao.update(user);
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
	
	public User getUserByUsername(String username){
		return userDao.getUserByUsername(username);
	}
	

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public boolean login_ok(String username, String password){
			return userDao.login_ok(username,password);		
	}
	
	public boolean duplicate_username(String username){
		return userDao.duplicate_username(username);
	}
}
