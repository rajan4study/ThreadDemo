/**
 * 
 */
package com.study.thread;

/**
 * @author
 *
 */
public class InterThreadCommunication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bucket b=new Bucket();
		new Producer(b);
		new Consumer(b);

	}

}

class Bucket{
	int count;
	boolean valueSet=false;
	
	public synchronized void changeCount(int count) {
		while(valueSet) {
			try {wait();} catch (InterruptedException e) {}
		}
		System.out.println("New Number: "+count);
		this.count=count;
		valueSet=true;
		notify();
		
	}
	
	public synchronized void getCount() {
		while(!valueSet) {
			try {wait();} catch (InterruptedException e) {}
		}
		System.out.println("Number is in bucket: " + count);
		valueSet=false;
		notify();
	}
}

class Producer implements Runnable{
	Bucket b;
	
	public Producer(Bucket b) {
		this.b = b;
		Thread t=new Thread(this, "Producer");
		t.start();
	}

	@Override
	public void run() {
		int count=0;
		while(true) {
			b.changeCount(count++);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		
	}
	
}
class Consumer implements Runnable{
	Bucket b;
	
	public Consumer(Bucket b) {
		this.b = b;
		Thread t=new Thread(this, "Consumer");
		t.start();
	}

	@Override
	public void run() {
		while(true) {
			b.getCount();
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		
	}
	
}
