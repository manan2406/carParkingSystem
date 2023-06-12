import java.sql.*;
import java.util.Scanner;

class JDBCServer{
    JDBCServer(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        }catch(ClassNotFoundException e){
            System.out.println(e);
            System.exit(0);
        }
    }

    public void insertCustomer(){
        String url="jdbc:mysql://localhost:3306/projectjava";
        String user="root";
        String pass="8161008@Mb";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Car Number:");
        int carNumber = sc.nextInt();
        System.out.println("Enter Owner Name:");
        String ownerName=sc.next();
        System.out.println("Enter Car Name:");
        String carName= sc.next();
        System.out.println("Enter total Charges:");
        double charges= sc.nextDouble();

        try(Connection connection = DriverManager.getConnection(url, user, pass)){
            int maxUnit=30;
            int insertedCustomer=0;
            String getUnit="select count(*) as maxunit from carparking";
            Statement statement = connection.createStatement();
            ResultSet resultset=statement.executeQuery(getUnit);
            while(resultset.next()){
                insertedCustomer=resultset.getInt("maxUnit"); 

            }
            if(insertedCustomer>30){
                System.out.println("You have excedded the maximum customer");
                System.exit(0);
            }

        }catch(SQLException e){
            System.out.println("Something went Wrong");
        }

        try(Connection connection =DriverManager.getConnection(url, user, pass)){


            String myQuery="insert into carparking values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(myQuery);
            preparedStatement.setString(1, ownerName);
            preparedStatement.setInt(2, carNumber);
            preparedStatement.setString(3,carName);
            preparedStatement.setDouble(4, charges);
            int affectedRows= preparedStatement.executeUpdate();
            System.out.println(affectedRows+" vehicle parked.");

            
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public void readCustomer(){
        String url="jdbc:mysql://localhost:3306/projectjava";
        String user="root";
        String pass="8161008@Mb";
        try(Connection connection= DriverManager.getConnection(url, user, pass)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from carparking");
                
                while(resultSet.next()){
                    System.out.printf("%s  %d  %s  %s%n",resultSet.getString("owner_name"),resultSet.getInt("car_number"),resultSet.getString("car_name"),resultSet.getString("charges"));

                }
        }catch(SQLException e){
            System.out.println(e);
            
        }
    } 

    public void CheckCustomer(){
        String url="jdbc:mysql://localhost:3306/projectjava";
        String user="root";
        String pass="8161008@Mb";
        try(Connection connection = DriverManager.getConnection(url, user, pass)){ 
        int maxUnit=30;
            int insertedCustomer=0;
            String getUnit="select count(*) as maxunit from carparking";
            Statement statement = connection.createStatement();
            ResultSet resultset=statement.executeQuery(getUnit);
            while(resultset.next()){
                insertedCustomer=resultset.getInt("maxUnit"); 

            }
            int emptySpaces=maxUnit-insertedCustomer;
            System.out.println(emptySpaces + " Empty spaces remaining out of 30");

        }catch(SQLException e){
            System.out.println("Something went Wrong");
        }
        
    } 

    public void deleteCustomer(){
        Scanner sc = new Scanner(System.in);
        String url="jdbc:mysql://localhost:3306/projectjava";
        String user="root";
        String pass="8161008@Mb";
        System.out.println("Enter Car number to delete");
        int numberD=sc.nextInt();
        try(Connection connection = DriverManager.getConnection(url, user, pass)){  
                String deleteQ= "delete from carparking where car_number = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQ);
                preparedStatement.setInt(1, numberD);
            
                int rowsDel=preparedStatement.executeUpdate();
                System.out.println(rowsDel+" record deleted from the table");
        }catch(SQLException e){
            System.out.println(e);
            System.out.println("Something went wrong");
        }
    }

    public void calculateTotalCharges() {
        String url = "jdbc:mysql://localhost:3306/projectjava";
        String user = "root";
        String pass = "8161008@Mb";
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String getTotalChargesQuery = "SELECT SUM(charges) AS total_charges FROM carparking";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getTotalChargesQuery);

            if (resultSet.next()) {
                double totalCharges = resultSet.getDouble("total_charges");
                System.out.println("Total charges collected: " + totalCharges);
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Something went wrong");
        }
    }



}
public class ConnectJDBC {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JDBCServer agent = new JDBCServer();
        int option;
        do {
            System.out.println("Menu");
            System.out.println("Option 1: Insert values");
            System.out.println("Option 2: See Table");
            System.out.println("Option 3: Check for empty spaces");
            System.out.println("Option 4: Remove Customer from table");
            System.out.println("Option 5: Total charges collected");
            System.out.println("Option 6: Exit");
            System.out.println("Enter your option:");
            option = sc.nextInt();
    
            switch (option) {
                case 1:
                    agent.insertCustomer();
                    break;
                case 2:
                    agent.readCustomer();
                    break;
                case 3:
                    agent.CheckCustomer();
                    break;
                case 4:
                    agent.deleteCustomer();
                    break;
                case 5:
                    agent.calculateTotalCharges();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.out.println("...Thank You for using the program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 6);
    }
}    