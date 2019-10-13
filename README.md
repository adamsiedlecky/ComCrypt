# ComCrypt - Spring Security & AES & Vaadin14

This project is a simple communicator which uses AES encryption. Whole application is centralised around account/s with OWNER role, which means that only them can create new accounts. Only users with ADMIN role can send anonymous messages (others send them with author's username). Only logged users (role USER) can write messages. Non-logged users can only display message by id and decrypt it with a key.
