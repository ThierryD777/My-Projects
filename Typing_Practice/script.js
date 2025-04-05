const quotes = [
    'Java is a class-based, object-oriented programming language, which makes it easy to design programs that are reusable and modular.',
    'In C, you have to manage memory manually — every pointer, every buffer overflow, every allocation counts.',
    'The first time you write a program in C, you"ll realize how closely the language is tied to the hardware, and that can be both powerful and tricky.',
    'Java"s garbage collector helps automate memory management, but understanding how objects are created and destroyed is still important for optimizing performance.',
    'When you learn C, you also learn how the computer interacts with the program at a very low level, which gives you a deeper understanding of memory and CPU usage.',
    'In Java, the `null` pointer exception is one of the most common errors, but with good null checks, you can avoid many problems.',
    'C"s preprocessor allows you to create powerful macros, but it can also lead to confusing and hard-to-maintain code if not used carefully.',
    'In Java, you don"t need to worry about freeing memory explicitly, but in C, you must call `free()` every time you allocate memory with `malloc()`.',
    'Java\'s `ArrayList` provides dynamic arrays, making it easier to work with data without worrying about memory management like you do in C with arrays.',
    'Pointers are one of the most powerful — and dangerous — features of C. They can lead to incredible efficiency, but they also can cause segmentation faults if misused.',
    'While Java"s object-oriented approach makes it perfect for large, complex systems, C"s procedural approach is often favored for performance-critical applications.',
    'In C, the standard library provides functions like `printf()` to display output, whereas in Java, `System.out.println()` does the same job with a more streamlined syntax.',
    'You"ll soon realize that understanding how Java handles exceptions can help you write more reliable, error-resistant code.',
    'In C, a `struct` is like a custom data type that can hold multiple variables of different types, useful for organizing complex data.',
    'Java"s extensive standard library is a great asset for developers, especially when working with files, databases, or networking tasks.',
    'In C, you have more control over your program"s execution, but you also have to take care of low-level details, like stack management and memory allocation.',
    'Java uses `interface` to provide abstraction and allows classes to implement multiple interfaces, unlike C, which relies on function pointers and structs for similar functionality.',
    'You"ll need to master the `for` loop, `while` loop, and `do-while` loop in C, as they form the foundation of iteration in this language.',
    'Java"s strong typing helps prevent many common bugs, but in C, you need to be cautious with type conversions to avoid issues like buffer overflows.',
    'In Java, exception handling with `try-catch` blocks makes your code more robust, while in C, handling errors often involves checking return codes and manually managing memory.',
    'Memory leaks are easier to manage in Java thanks to the garbage collector, but in C, if you forget to `free()` memory, your program could consume an increasing amount of system memory over time.',
    'Java\'s built-in multithreading support through the `Thread` class is very useful for parallel computing, but C requires more manual control with libraries like `pthread`.',
    'Writing a simple C program may feel like writing directly to the hardware, but that"s what makes it great for embedded systems and low-level programming.',
    'Java\'s use of automatic garbage collection allows developers to focus on writing code instead of manually managing memory allocation, unlike in C where memory must be manually allocated and freed.',
    'Learning C teaches you how to think about memory management and pointers, which is invaluable for optimizing software and working with system-level tasks.',
    'Java is platform-independent, thanks to the JVM (Java Virtual Machine), which allows you to run your Java programs on any system with a JVM installed.',
    'C"s low-level nature makes it ideal for developing operating systems and embedded applications where performance and memory usage are critical.',
    'In Java, everything is an object, and understanding object-oriented principles like inheritance, polymorphism, and encapsulation will help you write better, reusable code.',
    'C"s use of pointers allows direct manipulation of memory, but it also opens the door to hard-to-debug issues like segmentation faults and memory corruption.',
    'With Java"s `String` class, you don\'t need to worry about manually managing character arrays, unlike in C where you must be mindful of buffer sizes.',
    'In Java, the garbage collector automatically reclaims memory, reducing the risk of memory leaks, but in C, you must explicitly manage memory with functions like `malloc()` and `free()`.',
    'Java"s `String` class is immutable, meaning it can"t be changed after it\'s created. In C, however, strings are simply character arrays, and you can modify them freely.',
    'To become proficient in C, understanding the concept of `stack` and `heap` memory allocation is crucial, as it affects performance and the potential for memory leaks.',
    'In Java, the `synchronized` keyword helps manage multithreading by preventing multiple threads from executing critical sections of code simultaneously, which isn"t as straightforward in C.',
    'Java"s exception handling system is an elegant way to manage errors, whereas C requires you to manually check for error codes after each system call.',
    'When you start learning C, you\'ll spend a lot of time debugging memory issues, as the language provides no protection against out-of-bounds memory access or leaks.',
    'In Java, the `ArrayList` class provides a resizable array, while C requires you to manually allocate and resize arrays as needed, often leading to memory management issues.',
    'The syntax of C is concise and allows low-level operations, but mastering it requires attention to detail and discipline to avoid errors like buffer overruns.',
    'Java and C both have strong communities and plenty of resources, but Java is often more beginner-friendly due to its higher-level abstractions.',
    'Learning C can be challenging, but it teaches you to think critically about resource management and the underlying architecture of your computer.',
    'Java\'s `try-with-resources` statement automatically closes resources when they are no longer needed, simplifying resource management compared to C\'s manual `fclose()`.',
    'In C, arrays and pointers are often used interchangeably, which can be confusing for beginners but powerful once mastered.',
    'One of the key benefits of Java"s object-oriented design is the ability to create reusable and maintainable code through inheritance and polymorphism.',
    'In C, strings are an array of characters terminated by a null character, making them more flexible but also more prone to errors like buffer overflows.',
    'Mastering C means understanding how to avoid and debug memory issues like segmentation faults and memory leaks, as these can cause your program to crash.',
    'Java"s strong exception handling model with `try-catch-finally` blocks helps you recover gracefully from runtime errors.',
    'While C gives you complete control over system resources, Java abstracts these details, making it easier to focus on business logic rather than system intricacies.',
    'In C, the lack of built-in error handling can be tricky, and it"s important to manually check return values from system calls like `malloc()` and `fopen()`.',
    'Java"s `java.util` library provides powerful utilities for working with collections, date/time, and more, saving you the trouble of writing your own data structures from scratch.',
    'Learning C gives you a deeper understanding of how a program interacts with hardware, memory, and the operating system, which can help optimize both performance and security.',
    'Java"s `Thread` class and `Runnable` interface allow for easy multithreading, which is a more complex task in C where you might need to rely on libraries like `pthread`.'
];


let words = [];
let wordIndex = 0;
let startTime = Date.now();

const quoteElement = document.getElementById('quote');
const messageElement = document.getElementById('message');
const typedValueElement = document.getElementById('typed-value');

document.getElementById('start').addEventListener('click', function() {
    const quoteIndex = Math.floor(Math.random() * quotes.length);
    const quote = quotes[quoteIndex];

    words = quote.split(' ');
    //reset index for tracking
    wordIndex = 0;

    //Create an array of span elements so we can set a class
    const spanWords = words.map(function(word) {return `<span>${word} </span>`});

    quoteElement.innerHTML = spanWords.join('');
    quoteElement.childNodes[0].className = 'highlight';

    messageElement.innerText = '';

    typedValueElement.value = '';
    typedValueElement.focus();

    startTime = new Date().getTime();
});

typedValueElement.addEventListener('input', (e) => {
    const currentWord = words[wordIndex];
    const typedValue = typedValueElement.value;

    if(typedValue === currentWord && wordIndex === words.length - 1) {
        const elapsedTime = new Date().getTime() - startTime;
        const message = `CONGRATS😄! You finished in ${elapsedTime / 1000} seconds🏁`;
        messageElement.innerText = message;
    }else if(typedValue.endsWith(' ') && typedValue.trim() === currentWord) {
        typedValueElement.value = '';
        wordIndex++;

        for(const wordElement of quoteElement.childNodes) {
            wordElement.className = '';
        }

        quoteElement.childNodes[wordIndex].className = 'highlight';
    }else if(currentWord.startsWith(typedValue)) {
        typedValueElement.className = '';
    }else {
        typedValueElement.className = 'error';
    }
});
