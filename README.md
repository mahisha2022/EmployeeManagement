
# Rev Workforce - Enterprise Application (HRM)

Center of Excellence - 31-Mar-2023
Rev Workforce: Revolutionizing Human Resource Management for Modern Enterprises

Welcome to the Rev Workforce project, an enterprise application focused on HRM (Human Resource Management) with core functionalities like leave management, performance reviews, goal setting, and employee management, designed to improve efficiency and streamline HR operations.


## Authors

- [@Joseph Gonzalez](https://github.com/jojo1234526)
- [@Mahlet Drar](https://github.com/mahisha2022)
- [@Joseph Rebuck](https://github.com/joe7664)
- [@Sadrach Martinez](https://github.com/sadrachm)



## FAQ

#### Q1: How do I install Rev Workforce?


A: Installation instructions for users will be provided by the application owner or maintainer. For developers, please follow the Installation Instructions for Developers section in the README.
#### Q2: How can I contribute to the project?

A: To contribute to the project, follow the Contributor Expectations section in the README. Make sure you follow the contributing guidelines and pull request process when submitting changes.

#### Q3: Is there any support available for the application?

A: Support for the application is typically provided by the application owner or maintainer. If you find any issues or need assistance, please submit an issue on the GitHub repository.

#### Q4: How can I report a bug or request a feature?
A: To report a bug or request a feature, create a new issue in the GitHub repository. Provide as much information as possible, including steps to reproduce the bug or a detailed description of the feature you would like to see added.

#### Q5: Can I use Rev Workforce on mobile devices?
A: Yes, Rev Workforce is designed to be responsive across various devices, including desktops, tablets, and mobile devices.

#### Q6: How do I keep my data secure while using Rev Workforce?
A: Rev Workforce follows security best practices, including password hashing, session management with web tokens, and CORS API restrictions. For more information, refer to the Security Standards section in the README.

#### Q7: How can I stay updated on the latest developments and updates for Rev Workforce?
A: To stay updated on the latest developments and updates, follow the project's GitHub repository or join any available community forums or chat groups.

#### Q8: Can I use Rev Workforce with third-party applications?
A: Rev Workforce may support integration with third-party applications through its API. To request support for specific third-party applications, create a new issue in the GitHub repository.

#### Q9: How can I support the development of Rev Workforce?
A: If you would like to support the development and maintenance of Rev Workforce, please consider donating. For more information on how to donate, refer to the Support the Project section in the README.
## Usage/Examples

```javascript
import RevWorkforce from 'rev-workforce'

function App() {
  return (
    <RevWorkforce>
      <Employee>
        <LeaveManagement />
        <PerformanceModule />
      </Employee>

      <Manager>
        <LeaveManagement />
        <PerformanceModule />
      </Manager>

      <Admin>
        <General />
        <LeaveModule />
        <PerformanceModule />
      </Admin>
    </RevWorkforce>
  )
}


import EmployeeLeaveManagement from './components/EmployeeLeaveManagement'
import EmployeePerformanceModule from './components/EmployeePerformanceModule'
// ...and so on for other user components

function App() {
  return (
    <RevWorkforce>
      <Employee>
        <EmployeeLeaveManagement />
        <EmployeePerformanceModule />
      </Employee>
      // ...and so on for other user components
    </RevWorkforce>
  )
}

```


## Features


- Employee Features
    - Leave management system
        - View available leaves and applied leave data
        - Access holiday information
        - View organization structure for leave approval
    - Performance module
        - Submit performance review documents 
        - Praise or appreciate fellow employees
        - Set, accept or negotiate goals and targets
        - Request one-on-one meetings with managers
        - Provide anonymous feedback and suggestions


  - Additional features
    - View birthdays and work anniversaries of colleagues
- Manager Features
    - Leave management system
      - View team member availability
      - Approve or reject leave requests
   - Performance module
     - Review and provide feedback on employee performance
     - Set goals and targets for team members
     - Schedule one-on-one meetings with employees


- Admin Features
    - General
        - Create and manage user accounts
        - View employee details
    - Leave management system
        - Assign different types of leaves to employees
        - Revoke or cancel leaves upon request and approval
    - Performance module
        - View goals, targets, and feedback for all employees
        - Generate feedback and suggestions reports


## Installation

Install Rev Workforce by cloning both the Front End and Back End Repositories 

```bash
  Front End 
    git clone https://github.com/joe7664/EmployeeManagement.git
    npm install
    ng serve 
    
  Back End 
    git clone https://github.com/mahisha2022/EmployeeManagement.git

```
    
## Support

For support, email joseph093@revature.net or join our Slack channel. If you find this project helpful and would like to support its development, please consider donating. Your support is greatly appreciated and helps ensure the continued improvement and maintenance of the project.



