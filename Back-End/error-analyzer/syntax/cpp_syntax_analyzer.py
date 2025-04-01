import os
import re
import subprocess
import tempfile

from typing_extensions import override

from syntax.syntax_analyzer import SyntaxAnalyzer


class CppSyntaxAnalyzer(SyntaxAnalyzer):

    @override
    def check_syntax(self, code: str):
        with tempfile.NamedTemporaryFile(delete=False, suffix=".cpp") as temp_file:
            temp_file.write(code.encode())
            temp_file_path = temp_file.name
        try:
            result = subprocess.run(["g++", "-fsyntax-only", temp_file_path], capture_output=True, text=True)
            error_list = []
            if result.returncode != 0:
                for line in result.stderr.split("\n"):
                    match = re.search(r"(.*\.cpp):(\d+):(\d+): error: (.+)", line)
                    if match:
                        error_list.append({"lineNumber": int(match.group(2)), "message": match.group(4)})
        finally:
            os.remove(temp_file_path)
        return error_list