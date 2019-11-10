package Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CinemaDemo{
	public static void main(String[] args) {
		HappyCinema c=new HappyCinema("happy");
		Thread t1=new Thread(new Customer(c,"p1"));
		Thread t2=new Thread(new Customer(c,"p2"));
		t1.start();
		t2.start();
	}
	
}
class HappyCinema {
	private List<String> available=new ArrayList<>();
	private String name;
	public HappyCinema(String name) {
		super();
	    Initial();
	    System.out.println("欢迎光临！当前影院可用座位为："+this.available);
		this.name = name;
	}
	private void Initial() {
		available.add("1");
		available.add("2");
		available.add("3");
		available.add("4");
		available.add("5");
		available.add("6");
		available.add("7");
		available.add("8");
	}
	public String getName() {
		return name;
	}
    public List<String> getAvailable() {
		return available;
	}
	
	public boolean bookTicket(List<String> seats) {
    	List<String> copy=new ArrayList<>();
    	copy.addAll(available);
    	copy.removeAll(seats);
    	if((available.size()-seats.size())!=copy.size()) {
    		return false;
    	}
    	available=copy;
    	return true;
    }
}
class Customer implements Runnable{
	private HappyCinema cinema;
	private List<String> seats=new ArrayList<>();
	private String name;
	
	
	public Customer(HappyCinema cinema, String name) {
		this.cinema=cinema;
		this.name = name;
	}
	public void selectSeats() {
		Scanner scanner=new Scanner(System.in);
		System.out.println(this.name+"请输入购票数目：");
		int num=scanner.nextInt();
		System.out.println(this.name+"请选择座位：");
		while(num>0) {
			seats.add(scanner.next());
			num--;
		}
	}

	@Override
	public void run() {
		synchronized(this.cinema) {
			this.selectSeats();
			boolean flag=this.cinema.bookTicket(seats);
			if(flag) {
				System.out.println(this.name+"购票成功！"+"您的位置为："+this.seats);
				System.out.println("剩余座位："+this.cinema.getAvailable());
			}else {
				System.out.println(this.name+"购票失败!");
				System.out.println("剩余座位："+this.cinema.getAvailable());
			}
		}
	}
	
}
