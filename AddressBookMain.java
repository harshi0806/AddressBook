package com.adressbook;

import java.util.*;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.function.Function;

public class AddressBookMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your choice: \n 1=addContact \n 2=editContact \n 3=deleteContact \n 4=checkContact");
        final int input = scan.nextInt();
        final AddressBook addressBook = new AddressBookImpl();
        switch (input) {
            case 1:
                final ContactPerson contactPerson = AddressBookImpl.buildContactPerson();
                addressBook.addContactPerson(contactPerson);
                break;
            case 2:
                System.out.println("Enter user name: ");
                final String userName = scan.next();
                final ContactPerson existingContactPerson = addressBook.getContactPersonByName(userName);
                if (existingContactPerson == null) {
                    System.out.println("Contact does not exist.");
                } else {
                    addressBook.editContactPerson(existingContactPerson, userName);
                }
                break;
            case 3:
                System.out.println("Enter user name: ");
                final String keyValue = scan.next();
                addressBook.deleteContactPerson(keyValue);
                break;
            case 4:
                System.out.println("Enter user name: ");
                final String name = scan.next();
                addressBook.duplicateContactPerson(name);
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
    void editContactPerson(final ContactPerson contactPerson, final String userName);
    void deleteContactPerson(final String keyValue);
    ContactPerson duplicateContactPerson(String name);
    void sortAlphabetically(Map<String, ContactPerson> contactPersonMap);
    void sortByCity(Map<String, ContactPerson> contactPersonMap);
    void sortByState(Map<String, ContactPerson> contactPersonMap);
    void sortByZip(Map<String, ContactPerson> addressBook)
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
    public ContactPerson getContactPersonByName(final String name) {
        return contactPersonMap.get(name);
    }

    /**
     * This method is used to add multiple contact
     * @return the new contact value added
     */
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
    @Override
    public void editContactPerson(final ContactPerson contactPerson, final String userName) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter field: \n 1=address \n 2=city \n 3=state \n 4=zip \n 5=phone number");
        final int input = scan.nextInt();
        switch (input) {
            case 1:
                System.out.println("New address: ");
                scan.nextLine();
                String newAddress = scan.nextLine();
                contactPerson.setAddress(newAddress);
                break;
            case 2:
                System.out.println("New city: ");
                String newCity = scan.next();
                contactPerson.setCity(newCity);
                break;
            case 3:
                System.out.println("New state: ");
                String newState = scan.next();
                contactPerson.setState(newState);
            case 4:
                System.out.println("New zip: ");
                int newZip = scan.nextInt();
                contactPerson.setZip(newZip);
            case 5:
                System.out.println("New phone number: ");
                int newPhoneNumber = scan.nextInt();
                contactPerson.setPhoneNumber(newPhoneNumber);
            default:
                System.out.println("Invalid option");
        }
        contactPersonMap.put(userName, contactPerson);
    }

    @Override
    public void deleteContactPerson(final String keyValue) {
        boolean checkIfExist = contactPersonMap.containsKey(keyValue);
        if(checkIfExist == true) {
            contactPersonMap.remove(keyValue);
            System.out.println(contactPersonMap.get(keyValue));
        } else {
            System.out.println("Contact does not exist.");
        }
    }

    /**
     * Method to check for duplicate value using Java Streams
     * @param name
     * @return duplicateContact
     */
    @Override
    public ContactPerson duplicateContactPerson(String name) {
        boolean testIfDuplicate = contactPersonMap.keySet().stream().noneMatch(string -> string.equals(name));
        ContactPerson duplicateContact = testIfDuplicate == false ? contactPersonMap.get(name) : null;
        return  duplicateContact;
    }
    /**
     * Using Java Stream to Sort the Address Book Alphabetically
     * @param contactPersonMap
     */
    @Override
    public void sortAlphabetically(Map<String, ContactPerson> contactPersonMap) {
        Map<String, ContactPerson> contactsSortedAlphabetically = contactPersonMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, ContactPerson>comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));contactsSortedAlphabetically.forEach((name, contactPerson) -> {
            System.out.println();
            System.out.print(name);
            System.out.print(contactPerson);
            System.out.println();
        });
    }
    /**
     * Using Java Streams to Sort the Address Book by City, State & Zip
     * @param contactPersonMap
     */
    @Override
    public void sortByCity(Map<String, ContactPerson> contactPersonMap) {
        Map<String, ContactPerson> contactsSortedByCity = contactPersonMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, ContactPerson>comparingByValue(Comparator.comparing(ContactPerson::getCity)))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new));
        contactsSortedByCity.forEach((name, contactPerson) -> {
            System.out.println();
            System.out.print(name);
            System.out.print(contactPerson);
            System.out.println();
        });
    }

    @Override
    public void sortByState(Map<String, ContactPerson> contactPersonMap) {
        Map<String, ContactPerson> contactsSortedByState = contactPersonMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, ContactPerson>comparingByValue(Comparator.comparing(ContactPerson::getState)))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new));
        contactsSortedByState.forEach((name, contactPerson) -> {
            System.out.println();
            System.out.print(name);
            System.out.print(contactPerson);
            System.out.println();
        });
    }

    @Override
    public void sortByZip(Map<String, ContactPerson> addressBook) {
        Map<String, ContactPerson> contactsSortedByZip = contactPersonMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, ContactPerson>comparingByValue(Comparator.comparing(ContactPerson::getZip)))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new));
        contactsSortedByZip.forEach((name, contactPerson) -> {
            System.out.println();
            System.out.print(name);
            System.out.print(contactPerson);
            System.out.println();
        });
    }
    /**
     * Using Java Stream to Create City & State Dictionaries
     * @param contactPersonMap
     * @return cityDictionary
     */
    public Map<String, ContactPerson> buildCityDictionary (Map<String, ContactPerson> contactPersonMap) {
        Map<String, ContactPerson> cityDictionary = contactPersonMap.values()
                .stream()
                .collect(Collectors.toMap(ContactPerson::getCity,
                        Function.identity(),
                        (e1, e2) -> e1,
                        HashMap::new));
        return cityDictionary;
    }

    public Map<String, ContactPerson> buildStateDictionary (Map<String, ContactPerson> contactPersonMap) {
        Map<String, ContactPerson> stateDictionary = contactPersonMap.values()
                .stream()
                .collect(Collectors.toMap(ContactPerson::getState,
                        Function.identity(),
                        (e1, e2) -> e1,
                        HashMap::new));
        return stateDictionary;
    }

    /**
     * Using Java Stream searching Contacts by City & State
     * @param contactPersonMap
     * @param city, state
     */
    public void searchByCity(Map<String, ContactPerson> contactPersonMap, String city) {
        contactPersonMap.values()
                .stream()
                .filter(tempContact -> tempContact.getCity().equals(city))
                .forEach(System.out::println);
    }

    public void searchByState(Map<String, ContactPerson> contactPersonMap, String state) {
        contactPersonMap.values()
                .stream()
                .filter(tempContact -> tempContact.getState().equals(state))
                .forEach(System.out::println);
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
