// MongoDB Playground
// To disable this template go to Settings | MongoDB | Use Default Template For Playground.
// Make sure you are connected to enable completions and to be able to run a playground.
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.
// The result of the last command run in a playground is shown on the results panel.
// By default the first 20 documents will be returned with a cursor.
// Use 'console.log()' to print to the debug output.
// For more documentation on playgrounds please refer to
// https://www.mongodb.com/docs/mongodb-vscode/playgrounds/

// Select the database to use.
use('employee');

// Create the EmployeeInfo Table

const employeeInformation = [
    {
        "EmpID": 1,
        "EmpFname": "Sanjay",
        "EmpLname": "Mehra",
        "Department": "HR",
        "Project": "P1",
        "Address": "Hyderabad(HYD), Telangana",
        "DOB": "01/12/1976",
        "Gender": "M"
    },
    {
        "EmpID": 2,
        "EmpFname": "Ananya",
        "EmpLname": "Mishra",
        "Department": "Admin",
        "Project": "P2",
        "Address": "Delhi(DEL)",
        "DOB": "02/05/1968",
        "Gender": "F"
    },
    {
        "EmpID": 3,
        "EmpFname": "Rohan",
        "EmpLname": "Diwan",
        "Department": "Account",
        "Project": "P3",
        "Address": "Mumbai(BOM), Maharashtra",
        "DOB": "01/01/1980",
        "Gender": "M"
    },
    {
        "EmpID": 4,
        "EmpFname": "Sonia",
        "EmpLname": "Kulkarni",
        "Department": "HR",
        "Project": "P1",
        "Address": "Hyderabad(HYD), Telangana",
        "DOB": "02/05/1992",
        "Gender": "F"
    },
    {
        "EmpID": 5,
        "EmpFname": "Ankit",
        "EmpLname": "Kapoor",
        "Department": "Admin",
        "Project": "P2",
        "Address": "Delhi(DEL)",
        "DOB": "03/07/1994",
        "Gender": "M"
    }
    
]

const employeePosition = [
    {
        "EmpID": 1,
        "EmpPosition": "Manager",
        "DateOfJoining": "01/05/2022",
        "Salary": 500000
    },
    {
        "EmpID": 2,
        "EmpPosition": "Executive",
        "DateOfJoining": "02/05/2022",
        "Salary": 75000
    },
    {
        "EmpID": 3,
        "EmpPosition": "Manager",
        "DateOfJoining": "01/05/2022",
        "Salary": 90000
    },
    {
        "EmpID": 2,
        "EmpPosition": "Lead",
        "DateOfJoining": "02/05/2022",
        "Salary": 85000
    },
    {
        "EmpID": 1,
        "EmpPosition": "Executive",
        "DateOfJoining": "01/05/2022",
        "Salary": 300000
    }
]

// db.createCollection('EmployeeInfo')
// db.createCollection('EmployeePosition')
db.getCollection('EmployeeInfo').insertMany(
    [
        {
            "EmpID": 1,
            "EmpFname": "Sanjay",
            "EmpLname": "Mehra",
            "Department": "HR",
            "Project": "P1",
            "Address": "Hyderabad(HYD), Telangana",
            "DOB": "01/12/1976",
            "Gender": "M"
        },
        {
            "EmpID": 2,
            "EmpFname": "Ananya",
            "EmpLname": "Mishra",
            "Department": "Admin",
            "Project": "P2",
            "Address": "Delhi(DEL)",
            "DOB": "02/05/1968",
            "Gender": "F"
        },
        {
            "EmpID": 3,
            "EmpFname": "Rohan",
            "EmpLname": "Diwan",
            "Department": "Account",
            "Project": "P3",
            "Address": "Mumbai(BOM), Maharashtra",
            "DOB": "01/01/1980",
            "Gender": "M"
        },
        {
            "EmpID": 4,
            "EmpFname": "Sonia",
            "EmpLname": "Kulkarni",
            "Department": "HR",
            "Project": "P1",
            "Address": "Hyderabad(HYD), Telangana",
            "DOB": "02/05/1992",
            "Gender": "F"
        },
        {
            "EmpID": 5,
            "EmpFname": "Ankit",
            "EmpLname": "Kapoor",
            "Department": "Admin",
            "Project": "P2",
            "Address": "Delhi(DEL)",
            "DOB": "03/07/1994",
            "Gender": "M"
        }
        
    ]
);
db.getCollection('EmployeePosition').insertMany(
    [
        {
            "EmpID": 1,
            "EmpPosition": "Manager",
            "DateOfJoining": "01/05/2022",
            "Salary": 500000
        },
        {
            "EmpID": 2,
            "EmpPosition": "Executive",
            "DateOfJoining": "02/05/2022",
            "Salary": 75000
        },
        {
            "EmpID": 3,
            "EmpPosition": "Manager",
            "DateOfJoining": "01/05/2022",
            "Salary": 90000
        },
        {
            "EmpID": 2,
            "EmpPosition": "Lead",
            "DateOfJoining": "02/05/2022",
            "Salary": 85000
        },
        {
            "EmpID": 1,
            "EmpPosition": "Executive",
            "DateOfJoining": "01/05/2022",
            "Salary": 300000
        }
    ]
);


// QUESTION 1
// Write a query to fetch the EmpFname from the EmployeeInfo collection
db.getCollection('EmployeeInfo').find({EmpFname: {$exists:true}});

// QUESTION 2
// Write a query to fetch the number of employees working in the department 'HR'
db.getCollection('EmployeeInfo').countDocuments({Department: 'HR'});

// QUESTION 3
// Write a query to get the current date.
db.getCollection('EmployeeInfo').find({DOB: new Date()});

// QUESTION 4 
// Write a query to retrieve the first four characters of EmpLname from the
// EmployeeInfo collection.
db.getCollection('EmployeeInfo').aggregate({$project: {firstFour : {$substr: ["$EmpLname", 0, 4]}}});

// QUESTION 5 
// Write a query to fetch only the place name(string before brackets) from the address field of EmployeeInfo collection.
db.getCollection('EmployeeInfo').aggregate({$project: {PlaceName: {$regexFind: {input: "$Address", regex: /([^(]+)/}}}})

// QUESTION 6
// Write a query to find all the employees whose salary is between 50000 to 100000.
db.getCollection('EmployeeInfo').aggregate(
    {
        $lookup: {
            from: "EmployeePosition",
            localField: "EmpID",
            foreignField: "EmpID",
            as: "positionDetails"
        }
    },
    {
        $unwind: "$positionDetails"
    },
    {
        $match: {
            "positionDetails.Salary": {$gte: 50000, $lte: 100000}
        }
    }
);

// QUESTION 7
// Write a query to find the names of employees that begin with ‘S’
db.getCollection('EmployeeInfo').aggregate({
    $match: {
        EmpFname: {$regex : /^S/i }
    }
});

// QUESTION 8
// Write a query to retrieve the EmpFname and EmpLname in a single field
// “FullName”. The first name and the last name must be separated with space.
db.getCollection('EmployeeInfo').aggregate(
    {
        $project: {
            _id: 0,
            Fullname: {$concat: ["$EmpFname", " ", "$EmpLName"]}
        }
    }
);

// QUESTION 9
// Write a query to fetch all the records from the EmployeeInfo collection
// ordered by EmpLname in descending order and Department in the ascending
// order.
db.getCollection('EmployeeInfo').find().sort({ EmpLname: -1, Department: 1 });

//QUESTION 10
// Write a query to fetch details of all employees excluding the employees
// with first names, “Sanjay” and “Sonia” from the EmployeeInfo collection.
db.getCollection('EmployeeInfo').find({
    $or: [
        {EmpFname : {$ne: 'Sanjay'}},
        {EmpLname : {$ne: 'Sonia'}}
    ]
})

// QUESTION 11
// Write a query to fetch details of employees with the address as
// “DELHI(DEL)”.
db.getCollection('EmployeeInfo').find({
    Address : "Delhi(DEL)"
})

// QUESTION 12
// Write a query to fetch all employees who also hold the managerial position.
db.getCollection('EmployeeInfo').aggregate([
    {
      $lookup: {
        from: "EmployeePosition",
        localField: "EmpID",
        foreignField: "EmpID",
        as: "positions"
      }
    },
    {
      $match: {
        "positions.EmpPosition": "Manager"
      }
    }
]);

// QUESTION 13
// Write a query to fetch the department-wise count of employees sorted by
// department’s count in ascending order.
db.getCollection('EmployeeInfo').aggregate([
    {
      $group: {
        _id: "$Department",
        count: { $sum: 1 }
      }
    },
    {
      $sort: { count: 1 }
    },
    {
      $project: {
        Department: "$_id",
        count: 1,
        _id: 0
      }
    }
]);

// QUESTION 14
// Write a query to retrieve two minimum and maximum salaries from the
// EmployeePosition collection.
db.EmployeePosition.aggregate([
    {
      $group: {
        _id: null,
        minSalary: { $min: "$Salary" },
        maxSalary: { $max: "$Salary" },
      }
    },
    {
      $project: {
        _id: 0,
        minSalary: 1,
        maxSalary: 1
      }
    },
    {
      $sort: {
        minSalary: 1,
        maxSalary: 1
      }
    },
    {
      $limit: 2
    }
  ]);
  
// QUESTION 15
// Write a query to retrieve duplicate records from a collection.

// QUESTION 16
// Write a query to retrieve the list of employees working in the same
// department.
db.EmployeeInfo.aggregate([
    {
      $group: {
        _id: "$Department",
        employees: { $push: "$$ROOT" }
      }
    }
  ]);
  

// QUESTION 17
// Write a query to retrieve the last 3 records from the EmployeeInfo collection.
db.getCollection('EmployeePosition').find().sort({ _id: -1 }).limit(3);

// QUESTION 18
// Write a query to find the third-highest salary from the EmpPosition
// collection.
db.getCollection('EmployeePosition').find().sort({ Salary: -1 }).limit(1).skip(2);


// QUESTION 19
// Write a query to display the first and the last record from the EmployeeInfo
// collection.
db.getCollection('EmployeePosition').find().limit(1);
db.getCollection('EmployeePosition').find().sort({ _id: -1 }).limit(1);


// QUESTION 20
// Write a query to retrieve Departments who have less than 2 employees
// working in it.
db.getCollection('EmployeePosition').aggregate([
    {
      $group: {
        _id: "$Department",
        employeeCount: { $sum: 1 }
      }
    },
    {
      $match: {
        employeeCount: { $lt: 2 }
      }
    }
  ]);
  

// QUESTION 21
// Write a query to retrieve EmpPostion along with total salaries paid for each
// of them.
db.getCollection('EmployeePosition').aggregate([
    {
      $group: {
        _id: "$EmpPosition",
        totalSalary: { $sum: "$Salary" }
      }
    }
  ]);
  


// Run a find command to view items sold on April 4th, 2014.
// const salesOnApril4th = db.getCollection('sales').find({
//   date: { $gte: new Date('2014-04-04'), $lt: new Date('2014-04-05') }
// }).count();

// // Print a message to the output window.
// console.log(`${salesOnApril4th} sales occurred in 2014.`);

// // Here we run an aggregation and open a cursor to the results.
// // Use '.toArray()' to exhaust the cursor to return the whole result set.
// // You can use '.hasNext()/.next()' to iterate through the cursor page by page.
// db.getCollection('sales').aggregate([
//   // Find all of the sales that occurred in 2014.
//   { $match: { date: { $gte: new Date('2014-01-01'), $lt: new Date('2015-01-01') } } },
//   // Group the total sales for each product.
//   { $group: { _id: '$item', totalSaleAmount: { $sum: { $multiply: [ '$price', '$quantity' ] } } } }
// ]);
