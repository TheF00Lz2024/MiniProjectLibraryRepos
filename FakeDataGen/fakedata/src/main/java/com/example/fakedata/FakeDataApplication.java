package com.example.fakedata;

import com.example.fakedata.service.GenerateUserAccount;
import com.example.fakedata.service.LoginUser;

public class FakeDataApplication {

    public static void main(String[] args) {
//		GenerateBook generateBook = new GenerateBook();
//		generateBook.test();

        // call the login function in loginUser to get token
//        LoginUser loginUser = new LoginUser();
//        loginUser.userLogin("Test@gmail.com", "123456789");
		try{
            GenerateUserAccount generateUserAccount = new GenerateUserAccount();
            generateUserAccount.createUser();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
