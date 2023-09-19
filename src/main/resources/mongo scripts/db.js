db.bikes.insertMany(
    Array.from({ length: 10000 }).map((_, index) => ({
        name: ["Specialized", "Giant", "Trek", "Scott", "BMC", "Santa Cruz", "Norco", "Cube",][index % 8],
        type: ["MTB", "Downhill", "Freeride", "Gravel"][index % 4],
        pricePerHour: (index % 20) + 21,
        breed: [ "John", "Mike", "Tom", "Jack", "Thomas", "Jonny", "martin",][index % 7], index: index,
    }))
);
