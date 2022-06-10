package carsharing;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuService {
    Scanner scanner = new Scanner(System.in);
    QueryService queryService = new QueryService();

    String firstMenu =
            "1. Log in as a manager\n" +
            "2. Log in as a customer\n" +
            "3. Create a customer\n" +
            "0. Exit";
    String customerCarMenu =
            "1. Rent a car\n" +
            "2. Return a rented car\n" +
            "3. My rented car\n" +
            "0. Back";
    String customerRentedCar =
            "Your rented car:\n" +
            "%s\n" +
            "Company:\n" +
            "%s\n";
    String managerMenu =
            "1. Company list\n" +
            "2. Create a company\n" +
            "0. Back";
    String carMenu =
            "1. Car list\n" +
            "2. Create a car\n" +
            "0. Back";
    String companyMenu = "Choose a company:";
    String customerMenuDisplay = "Choose a customer:";
    String carMenuDisplay = "Choose a car:";
    String companyNameMenu = "'%s' company\n";
    String companyNamePrompt = "Enter the company name:";
    String companyNameResponse = "The company was created!";
    String carListResponse = "The car list is empty!";
    String companyListResponse = "The company list is empty!";
    String customerListResponse = "The customer list is empty!";
    String carNamePrompt = "Enter the car name:";
    String carListDisplay = "Car list:";
    String carNameResponse = "The car was added!";
    String customerNamePrompt = "Enter the customer name:";
    String customerNameResponse = "The customer was added!";
    String returnACarFailResponse = "You didn't rent a car!";
    String rentACarSuccessResponse = "You rented '%s'\n";
    String rentACarFailResponse = "You've already rented a car!";
    String returnACarSuccessResponse = "You've returned a rented car!";

    public void createACompany(Scanner scanner) {
        System.out.println(companyNamePrompt);
        boolean bool = queryService.createACompany(scanner);
        if(bool) {
            System.out.println(companyNameResponse);
        }
    }

    public boolean loginAsCustomer() {
        List<Customer> customerList = queryService.loginInAsCustomer();
        boolean bool = true;
        if(customerList.isEmpty()) {
            System.out.println(customerListResponse);
            bool = false;
        } else {
            System.out.println(customerMenuDisplay);
            AtomicInteger i = new AtomicInteger();
            customerList.forEach(customer -> System.out.println(i.getAndIncrement() + 1 + ". " + customer.getName()));
            System.out.println("0. Back");
        }
        return bool;
    }

    public void createACar(int company_id, Scanner scanner) {
        System.out.println(carNamePrompt);
        boolean bool = queryService.createACar(company_id, scanner);
        if(bool) {
            System.out.println(carNameResponse);
        }
    }

    public void createACustomer(Scanner scanner) {
        System.out.println(customerNamePrompt);
        boolean bool = queryService.CreateACustomer(scanner);
        if(bool) {
            System.out.println(customerNameResponse);
        }
    }

    public boolean viewCompanyList() {
        boolean bool = true;
        List<Company> companyList = queryService.viewAllCompanies();
        if (companyList.isEmpty()) {
            System.out.println(companyListResponse);
            bool = false;
        } else {
            System.out.println(companyMenu);
            AtomicInteger i = new AtomicInteger();
            companyList.forEach(company -> System.out.println(i.getAndIncrement() + 1 + ". " + company.getName()));
            System.out.println("0. Back");
        }
        return bool;
    }

    public void viewCompanyMenu(int id) {
        String companyName = queryService.selectACompany(id);
        System.out.printf(companyNameMenu, companyName);
        System.out.println(carMenu);
    }

    public void viewCarList(int id) {
        List<Car> carList = queryService.viewAllCars(id);
        if(carList.isEmpty()) {
            System.out.println(carListResponse);
        } else {
            System.out.println(carListDisplay);
            AtomicInteger i = new AtomicInteger();
            carList.forEach(car -> System.out.println(i.getAndIncrement() + 1 + ". " + car.getName()));
        }
    }

    public void viewRentedCar(int customer_id) {
        int car_id = queryService.rentedCarByCustomer(customer_id);
        if(car_id == 0) {
            System.out.println(returnACarFailResponse);
        } else {
            Car car = queryService.selectACarById(car_id);
            String companyName = queryService.selectACompany(car.getCompany_id());
            System.out.printf(customerRentedCar, car.getName(), companyName);
        }
    }

    public void mainMenu() {
        do {
            System.out.println(firstMenu);
            int menuOption = Integer.parseInt(scanner.nextLine());
            switch (menuOption) {
                case 1:
                    setManagerMenu();
                    break;
                case 2:
                    boolean bool = loginAsCustomer();
                    if (bool) {
                        int customerId = Integer.parseInt(scanner.nextLine());
                        if(customerId != 0) {
                            setCustomerCarMenu(customerId);
                        }
                    }
                    break;
                case 3:
                    createACustomer(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid Command!");
                    break;
            }
        } while(true);
    }

    public void setManagerMenu() {
        do {
            System.out.println(managerMenu);
            int managerOption = Integer.parseInt(scanner.nextLine());
            switch (managerOption) {
                case 1:
                    boolean bool = viewCompanyList();
                    if(bool) {
                        int companyOption = Integer.parseInt(scanner.nextLine());
                        if (companyOption != 0) {
                            setCompanyMenu(companyOption);
                        }
                    }
                    break;
                case 2:
                    createACompany(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        } while (true);
    }

    public void setCompanyMenu(int id) {
        do {
            viewCompanyMenu(id);
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    viewCarList(id);
                    break;
                case 2:
                    createACar(id, scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid command!");
            }
        } while (true);
    }

    public void setCustomerCarMenu(int customer_id) {
        do {
            System.out.println(customerCarMenu);
            int customerOption = Integer.parseInt(scanner.nextLine());
            switch (customerOption) {
                case 1:
                    if (queryService.rentedCarByCustomer(customer_id) == 0) {
                        boolean bool = viewCompanyList();
                        if(bool) {
                            int companyChoice = Integer.parseInt(scanner.nextLine());
                            List<Car> carList = queryService.getAvailableCar(companyChoice);
                            if(carList.isEmpty()) {
                                System.out.println(carListResponse);
                            } else {
                                System.out.println(carMenuDisplay);
                                AtomicInteger i = new AtomicInteger();
                                carList.forEach(car -> System.out.println(i.getAndIncrement() + 1 + ". " + car.getName()));
                                System.out.println("0. Back");
                                int carChoice = Integer.parseInt(scanner.nextLine());
                                if(carChoice != 0) {
                                    boolean isTrue = queryService.rentACar(carChoice, customer_id);
                                    if (isTrue) {
                                        System.out.printf(rentACarSuccessResponse, queryService.selectACarById(carChoice).getName());
                                        System.out.println("carChoice: " + carChoice + "customer_id: " + customer_id);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println(rentACarFailResponse);
                    }
                    break;
                case 2:
                    int carId = queryService.rentedCarByCustomer(customer_id);
                    if (carId != 0) {
                        boolean bool = queryService.returnACar(customer_id, carId);
                        if(bool) {
                            System.out.println(returnACarSuccessResponse);
                        }
                    } else {
                        System.out.println(returnACarFailResponse);
                    }
                    break;
                case 3:
                    viewRentedCar(customer_id);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid Command!");
            }
        } while(true);
    }
}