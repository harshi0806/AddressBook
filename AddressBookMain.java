package com.adressbook;

import java.util.*;
import java.util.Scanner;

public class AddressBookMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your choice: \n 1=addContact \n 2=editContact");
        final int input = scan.nextInt();
        switch (input) {
            case 1 :
                final ContactPerson contactPerson = AddressBookImpl.buildContactPerson();
                final AddressBook addressBook = new AddressBookImpl();
                addressBook.addContactPerson(contactPerson);
                break;
            case 2 :
                System.out.println("Enter field");
                break;
            default:
                System.out.println("Invalid Input. Choose from option given");
        }
    }
}
/**
 * interface acts as a console to add Contact Person details to Address Book
 */
interface AddressBook {
    void addContactPerson(final ContactPerson contactPerson);
    void editContactPerson(final ContactPerson contactPerson);
    ContactPerson getContactPersonByName(final String name);
}

class AddressBookImpl implements AddressBook {
    private Map<String, ContactPerson> contactPersonMap;

    public AddressBookImpl() {
        contactPersonMap = new HashMap<>();
    }

    @Override
    public void addContactPerson(final ContactPerson contactPerson) {
        contactPersonMap.put(contactPerson.getName(), contactPerson);
        System.out.println(contactPersonMap);
    }

    @Override
    public void editContactPerson(final ContactPerson contactPerson) {
        contactPersonMap.put(contactPerson.getName(), contactPerson);
        System.out.println(contactPersonMap);

    }

    @Override
    public ContactPerson getContactPersonByName(final String name) {
        return contactPersonMap.get(name);
    }

    public static ContactPerson buildContactPerson() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter first name: ");
        String firstName = scan.next();
        System.out.println("Enter last name: ");
        String lastName = scan.next();
        System.out.println("Enter address: ");
        scan.nextLine();
        String address = scan.nextLine();
        System.out.println("Enter city: ");
        String city = scan.next();
        System.out.println("Enter state: ");
        String state = scan.next();
        System.out.println("Enter zip: ");
        int zip = scan.nextInt();
        System.out.println("Enter phone number: ");
        int phoneNumber = scan.nextInt();
        String name = firstName + " " + lastName;
        return new ContactPerson(name, firstName, lastName, address, city, state, zip, phoneNumber);
    }
}


/**
 * Another class ContactPerson which contains details of person
 */
class ContactPerson {
    private String name;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int zip;
    private int phoneNumber;

    //Default Constructor
    public ContactPerson() {

    }

    //Parameterized Constructor
    public ContactPerson(String name, String firstName, String lastName, String address, String city, String state, int zip, int phoneNumber) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter and Setter methods
     *
     * @return Corresponding value of variables
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String setName(String firstName, String lastName) {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}