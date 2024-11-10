//importing utility package
import java.util.*;

//make car class
class car{
    private String carid;
    private String brand;
    private String model;
    private double basepriceperday;
    private boolean isavailable;
    //method that is consist of carid, brand, mode,basepriceperday
    public car(String carid,String brand,String model,double basepriceperday){
        this.carid=carid;
        this.brand=brand;
        this.model=model;
        this.basepriceperday=basepriceperday;
        this.isavailable=true;
    }
    //make getter and setter
    public String getCarid(){
        return carid;
    }
    
    public String getbrand(){
        return brand;
    }
    
    public String getModel(){
        return model;
    }
    //method that calclate price
    public double calculateprice(int rentaldays){
        return basepriceperday*rentaldays;
    }
    
    public boolean isavailable(){
        return isavailable;
    }
    
    public void rent(){
        isavailable=false;
    }
    
    public void returncar(){
        isavailable=true;
    }
}
//make customer class
class customer{
    //make private customerid, name.
    private String customerid;
    private String name;
    //method that consist of customerid, name
    public customer(String customerid,String name){
        this.customerid=customerid;
        this.name=name;
    }
    
    public String getCustomerid(){
        return customerid;
    }
    
    public String getName(){
        return name;
    }
}
//make rental class
class rental{
    //make private attributes like car and customer
    private car car;
    private customer customer;
    private int days;
    public rental(car car,customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public car getCar(){
        return car;
    }
    
    public customer getCustomer(){
        return customer;
    }
    
    public int getDays(){
        return days;
    }
}

class carrentalsystem{
    //make private list of cars ,customer ,rentals.
    private List<car>cars;
    
    private List<customer>customers;
    
    private List<rental>rentals;
    
    public carrentalsystem(){
        //make objects in carrentalsystem
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }
    
    public void addcar(car car){
        cars.add(car);
    }
    
    public void addcustomer(customer customer){
        customers.add(customer);
    }
    
    public void rentcar(car car,customer customer,int days){
        //condition
        if(car.isavailable()){
            car.rent();
            rentals.add(new rental(car,customer,days));
        }
        else{
            System.out.println("car is not available for rent.");
        }
    }
    
    public void returncar(car car){
        car.returncar();
        rental rentaltoremove=null;
        for(rental rental:rentals){
            if(rental.getCar()==car){
                rentaltoremove=rental;
                break;
            }
        }
        if(rentaltoremove!=null){
            rentals.remove(rentaltoremove);
            System.out.println("car returned successfully.");
        }else{
            System.out.println("car was not rented.");
        }
    }
    
    public void menu(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            System.out.println("***** Car Rental System *****");
            System.out.println("1.Rent a car");
            System.out.println("2.Return a car");
            System.out.println("3.Exit");
            System.out.println("Enter your choice:");
            
            int choice=scanner.nextInt();
            scanner.nextLine();
            if(choice==1){
                System.out.println("\n==Rent a car==\n");
                System.out.println("Enter your name:");
                String customerName=scanner.nextLine();

                System.out.println("\nAvailable cars:");
                for(car car:cars){
                    if(car.isavailable()){
                        System.out.println(car.getCarid()+"-"+car.getbrand()+"-"+car.getModel());
                    }
                }
                System.out.println("\nEnter the car Id you want to rent:");
                String carid=scanner.nextLine();

                System.out.println("\nEnter the number of days for rental:");
                int rentaldays=scanner.nextInt();
                scanner.nextLine();
                
                customer newcustomer=new customer("cus"+(customers.size()+1),customerName);
                addcustomer(newcustomer);
                
                car selectedcar=null;
                for(car car:cars){
                    if(car.getCarid().equals(carid) && car.isavailable()){
                        selectedcar=car;
                        break;
                    }
                }
                if(selectedcar!=null){
                    double totalprice=selectedcar.calculateprice(rentaldays);
                    System.out.println("\n==Rental Information==\n");
                    System.out.println("Customer Id"+newcustomer.getCustomerid());
                    System.out.println("Customer Name"+newcustomer.getName());
                    System.out.println("Car"+selectedcar.getbrand()+" "+selectedcar.getModel());
                    System.out.println("Rental Days"+rentaldays);
                    System.out.printf("Total price:"+totalprice);

                    System.out.print("\nConfirm rental (Y/n):");
                    String confirm=scanner.nextLine();

                    if(confirm.equalsIgnoreCase("y")){
                        rentcar(selectedcar,newcustomer,rentaldays);
                        System.out.println("\nCar rented successfully.");
                    }else{
                        System.out.println("\nRental canceled.");
                    }
                }else{
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            }else if(choice==2){
                System.out.println("\n==Return a car==\n");
                System.out.println("Enter the car ID you want to return:");
                String carID=scanner.nextLine();

                car cartoreturn=null;
                for(car car:cars){
                    if(car.getCarid().equals(carID) && car.isavailable()){
                        cartoreturn=car;
                        break;
                    }
                }
                if(cartoreturn!=null){
                    customer customer=null;
                    for(rental rental:rentals){
                        if(rental.getCar()==cartoreturn){
                            customer=rental.getCustomer();
                            break;
                        }
                    }
                    if(customer!=null){
                        returncar(cartoreturn);
                        System.out.println("car returned successfully by"+customer.getName());
                    }else{
                        System.out.println("car was not rented or renatl information is missing.");
                    }
                }else{
                    System.out.println("Invalid car ID or car is not rented.");
                }
            }else if(choice==3){
                break;
            }else{
                System.out.println("Invalid choice.Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the car Rental system!");
    }
    
    
        
}

public class Main{
    public static void main(String[] args) {
        carrentalsystem rentalsystem=new carrentalsystem();
        car car1=new car("C001","Toyota","Carry",60);
        car car2=new car("C002","Honda","Accord",70);
        car car3=new car("C003","Mahindra","Thar",110);
        rentalsystem.addcar(car1);
        rentalsystem.addcar(car2);
        rentalsystem.addcar(car3);

        rentalsystem.menu();
    }
}