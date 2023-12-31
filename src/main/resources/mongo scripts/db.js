db.bikes.insertMany(
    Array.from({ length: 10000 }).map((_, index) => ({
        name: ["Specialized", "Giant", "Trek", "Scott", "BMC", "Santa Cruz", "Norco", "Cube",][index % 8],
        type: ["MTB", "Downhill", "Freeride", "Gravel"][index % 4],
        pricePerHour: (index % 20) + 21,
        owner: [ "John", "Mike", "Tom", "Jack", "Thomas", "Jonny", "martin",][index % 7], index: index,
    }))
);

db.users.insertMany(
    Array.from({ length: 10000 }).map((_, index) => ({
        name: ["John", "Mike", "Tom", "Jack", "Thomas", "Jonny", "Martin"][Math.floor(Math.random() * 7)],
        surname: ["Smith", "Johnson", "Brown", "Wilson", "Lee", "Davis", "Evans"][Math.floor(Math.random() * 7)],
        phonenumber: ["555-1234", "555-5678", "555-9876", "555-4321", "555-8765"][Math.floor(Math.random() * 5)],
        email: `email${index}@${["example.com", "gmail.com", "yahoo.com", "hotmail.com"][Math.floor(Math.random() * 4)]}`
    }))
);
