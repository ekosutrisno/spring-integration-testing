# Unit and Integration Testing

> Project ini merupakan sample Unit Testing untuk Layer `Repository` dan `Service`, serta Integration Testing untuk `Controller`.

- Scenario Testing

    - GetAllStudent
    - AddStudent (Success and Fail)
    - DeleteStudent (Success and Fail)

- Add Student

    - Method `POST`
    - Content-Type `applicaton/json`
    - Accept `application/json`
    - path `/api/v1/students`
    - Request (void)

        - body

          ```json
          {
            "name": "string",
            "email": "string | (email_format)",
            "gender": "MALE"
          }
          ```

- Get All Student

    - Method `GET`
    - path `/api/v1/students`
    - Request (Student)

        - body

          ```json
          {
            "id": "number",
            "name": "string",
            "email": "string | (email_format)",
            "gender": "MALE | FEMALE"
          }
          ```

- Delete Student

    - Method `DELETE`
    - path `/api/v1/students/{studentID}` => `studentId (number)`
    - Request (void)
