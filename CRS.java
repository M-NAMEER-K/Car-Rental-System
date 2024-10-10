import java.util.ArrayList;
import java.util.Scanner;


    class Car{
        private String carid;
        private String brand;
        private String model;
        private double baseprice_per_day;
        private boolean isAvailable;
    
    
        public Car(String carid,String brand,String model,double baseprice_per_day){
            this.carid=carid;
            this.brand=brand;
            this.model=model;
            this.baseprice_per_day=baseprice_per_day;
            this.isAvailable=true;
        }
    
        public String getcarid(){
            return carid;
        }
        public String getbrand(){
            return brand;
        }
        public String getmodel(){
            return model;
        }
        public double calculateprice(int rentalDays){
            return baseprice_per_day*rentalDays;
        }
        public boolean isAvailable(){
            return isAvailable;
        }
        public void rent(){
            isAvailable=false;
        }
        public void returncar(){
            isAvailable=true;
        }
    
    }
    class Customer{
       private String customerid;
       private String name;
      
       public Customer(String customerid,String name){
        this.customerid=customerid;
        this.name=name;
       }
       public String getcustomerid(){
        return customerid;
       }
       public String getname(){
        return name;
       }
       
    }
    class Rental{
               private Car car;
               private Customer customer;
               private int days;
               
               public Rental(Car car,Customer customer,int days){
                  this.car=car;
                  this.customer=customer;
                  this.days=days; 
               }
               public Car getcar(){
                return car;
               }
               public Customer getcustomer(){
                return customer;
               }
               public int getdays(){
                return days;
               }
    }
    class CarRentalSystem{
          private ArrayList<Car> cars;
          private ArrayList<Customer> customers;
          private ArrayList<Rental> rentals;


          public CarRentalSystem(){
            cars=new ArrayList<>();
            customers=new ArrayList<>();
            rentals=new ArrayList<>();
          }

          public void addcar(Car car){
            cars.add(car);
          }
          public void addcustomer(Customer customer){
            customers.add(customer);
          }
          public void rentcar(Car car,Customer customer,int days){
            if(car.isAvailable()==true){
                car.rent();
                rentals.add(new Rental(car,customer,days));
            }
            else{
                System.out.println("Car is Not Available for rent..");
            }
          }
          public void returncar (Car car){
            Rental rentaltoremove=null;
            for(Rental rental:rentals){
                if(rental.getcar()==car){
                    rentaltoremove=rental;
                    break;
                }
            }
            if(rentaltoremove!=null){
                rentals.remove(rentaltoremove);
               
                car.returncar();
            }
            else{
                System.out.println("car was not rented");
            }
          }

          public void menu(){
          Scanner sc=new Scanner(System.in);
               while(true){
                  System.out.println("===Car Rental System===");
                  System.out.println("1 Rent a car");
                  System.out.println("2 Return a car");
                  System.out.println("3 Exit");
                  System.out.println("Enter your choice:");
                  int choice=sc.nextInt();
                  if(choice==1){
                    System.out.println("====Rent a Car====");
                    System.out.println("Enter your name:");
                    String customername=sc.nextLine();
                    System.out.println("Available Cars");
                    for(Car car : cars){
                        if(car.isAvailable()){
                            System.out.println(car.getcarid()+"--"+car.getbrand()+" "+car.getmodel());
                        }
                    }
                    System.out.println("Enter the car id you want to rent:");
                    String carid=sc.nextLine();
                    System.out.println("Enter the number of days for rental:");
                    int rentaldays=sc.nextInt();
                    sc.nextLine();
                    Customer newcustomer=new Customer("CUS"+(customers.size()+1),customername);
                    addcustomer(newcustomer);

                   Car selectedcar=null;
                   for(Car car:cars){
                    if(car.getcarid().equals(carid) &&car.isAvailable()){
                        selectedcar=car;
                        break;
                    }
                   }
                   if(selectedcar!=null){
                    double totalprice=selectedcar.calculateprice(rentaldays);
                    System.out.println("== Rental Information==");
                    System.out.println("Customer id:"+newcustomer.getcustomerid());
                    System.out.println("customer name:"+newcustomer.getname());
                    System.out.println("car:"+ selectedcar.getbrand()+" "+selectedcar.getmodel() );
                    System.out.println("Rental Days:"+ rentaldays);
                    System.out.printf("Total Price: $%.2f%n",totalprice);

                    System.out.println("Confirm rental(Y/N):");
                    String confirm=sc.nextLine();
                    if(confirm.equalsIgnoreCase("Y")){
                        rentcar(selectedcar,newcustomer,rentaldays);
                        System.out.println("Car rented successfully");
                    }
                    else{
                        System.out.println("Rental canceled");
                    }
                   }     
                   else{
                    System.out.println("Invalid car selection or car not available for selection");
                   }   
                  }
                  else if(choice==2){
                    System.out.println("==Return a car==");
                    System.out.println("enter the car id you want to return:");
                    String carid=sc.nextLine();
                    Car cartoreturn=null;
                    for(Car car:cars){
                        if(car.getcarid().equals(carid) && !car.isAvailable()){
                            cartoreturn=car;
                            break;
                        }
                    }
                    if(cartoreturn!=null){
                        Customer customer=null;
                        for(Rental rental:rentals){
                            if(rental.getcar()==cartoreturn){
                                customer=rental.getcustomer();
                                break;
                            }
                        }
                        if(customer!=null){
                            returncar(cartoreturn);
                            System.out.println("car returned successfully by "+customer.getname());
                        }
                        else{
                            System.out.println("Car was not rented or rental information is missing");
                        }
                    }
                    else{
                        System.out.println("Invalid car id or car is not rented");
                    }
                  }
                  else if(choice==3){
                    break;
                  }
                  else{
                      System.out.println("invalid choice.please enter a valid option");
                  }
                }
                  System.out.println("Thank you for using the car rental system!");
               
          }
}

public class CRS {
    public static void main(String args[]){
        CarRentalSystem rentalsystem=new CarRentalSystem();
        Car car1=new Car("C001","Toyota","Camry",60.0);
        Car car2=new Car("C002","Honda","Accord",70.0);
        Car car3=new Car("C003","Audi","R8",200.0);
        rentalsystem.addcar(car1);
        rentalsystem.addcar(car2);
        rentalsystem.addcar(car3);
        rentalsystem.menu();
    }
}
