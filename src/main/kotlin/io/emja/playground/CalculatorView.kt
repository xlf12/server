package io.emja.server

import org.springframework.web.bind.annotation.*

/**
 * Controller class information purposes.
 */
@RestController
@RequestMapping("/hi")
class CalculatorView(val calculatorService: CalculatorService) {

    @GetMapping("/form")
    fun showForm(): String {
        return """
            <html>
                <head>
                    <title>Calculator</title>
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #f0f0f0; text-align: center; padding: 50px; }
                        h1 { color: #333; }
                        form { background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); display: inline-block; }
                        label { display: block; margin: 10px 0 5px; }
                        input { padding: 10px; width: calc(100% - 22px); margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }
                        button { padding: 10px 20px; background-color: #007BFF; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
                        button:hover { background-color: #0056b3; }
                    </style>
                </head>
                <body>
                    <h1>Calculator</h1>
                    <form action="/hi/calculate" method="post">
                        <label for="a">First Number:</label>
                        <input type="text" id="a" name="a" required><br><br>
                        <label for="op">Operation:</label>
                        <input type="text" id="op" name="op" required><br><br>
                        <label for="b">Second Number:</label>
                        <input type="text" id="b" name="b" required><br><br>
                        <button type="submit">Calculate</button>
                    </form>
                </body>
            </html>
        """.trimIndent()
    }

    @PostMapping("/calculate")
    fun calculate(@RequestParam a: String, @RequestParam b: String, @RequestParam op: String): String {

        if (!isFloat(a) || !isFloat(b)) {
            return """
                <html>
                    <head>
                        <title>Calculation Error</title>
                        <style>
                            body { font-family: Arial, sans-serif; background-color: #f0f0f0; text-align: center; padding: 50px; }
                            h1 { color: #333; }
                            a { text-decoration: none; color: #007BFF; }
                            a:hover { text-decoration: underline; }
                        </style>
                    </head>
                    <body>
                        <h1>Error: Invalid input. Please enter valid numbers.</h1>
                        <a href="/hi/form">Back to Calculator</a>
                    </body>
                </html>
            """.trimIndent()
        }

        val result = calculatorService.performCalculation(a.toFloat(), b.toFloat(), op)

        return """
            <html>
                <head>
                    <title>Calculation Result</title>
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #f0f0f0; text-align: center; padding: 50px; }
                        h1 { color: #333; }
                        a { text-decoration: none; color: #007BFF; }
                        a:hover { text-decoration: underline; }
                    </style>
                </head>
                <body>
                    <h1>Result: $result</h1>
                    <a href="/hi/form">Back to Calculator</a>
                </body>
            </html>
        """.trimIndent()
    }

    private fun isFloat(value: String): Boolean {
        return value.toFloatOrNull() != null
    }
}