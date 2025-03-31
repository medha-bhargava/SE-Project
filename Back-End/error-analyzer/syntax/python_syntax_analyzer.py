import ast

from syntax.syntax_analyzer import SyntaxAnalyzer


class PythonSyntaxAnalyzer(SyntaxAnalyzer):
    def check_syntax(self, code: str):
        error_list = []
        try:
            ast.parse(code)
        except SyntaxError as e:
            error_message = e.msg
            if "(detected at line" in error_message:
                error_message = error_message.split(" (detected at line")[0]
            error_list.append({"lineNumber": e.lineno, "message": error_message})
        return error_list