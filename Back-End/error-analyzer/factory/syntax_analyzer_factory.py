from syntax.cpp_syntax_analyzer import CppSyntaxAnalyzer
from syntax.java_syntax_analyzer import JavaSyntaxAnalyzer
from syntax.python_syntax_analyzer import PythonSyntaxAnalyzer
from syntax.syntax_analyzer import SyntaxAnalyzer


class SyntaxAnalyzerFactory:

    @staticmethod
    def get_syntax_analyzer(language: str) -> SyntaxAnalyzer:
        if language == "Python":
            return PythonSyntaxAnalyzer()
        elif language == "Java":
            return JavaSyntaxAnalyzer()
        elif language == "Cpp":
            return CppSyntaxAnalyzer()
        else:
            raise ValueError("Unsupported language")