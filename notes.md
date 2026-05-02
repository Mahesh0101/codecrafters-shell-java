--

- **`cmd.equals("exit")`** — Compares the actual **content** of the strings (correct for user input)
- **`cmd == "exit"`** — Compares if they're the **same object in memory** (unreliable for user input)

Your code uses the correct approach. When a user enters "exit" via the scanner, it creates a new String object in memory. Using `==` might fail because the objects are different, even if the text is identical. `equals()` reliably compares the actual string values, regardless of which object they are.

**Best practice:** Always use `equals()` for string content comparison in Java.