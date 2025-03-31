import os
import re
import subprocess
import tempfile

from typing_extensions import override

from syntax.syntax_analyzer import SyntaxAnalyzer


class JavaSyntaxAnalyzer(SyntaxAnalyzer):

    @override
    def check_syntax(self, code: str):
        with tempfile.NamedTemporaryFile(delete=False, suffix=".java") as temp_file:
            temp_file.write(code.encode())
            temp_file_path = temp_file.name

        try:
            result = subprocess.run(["javac", temp_file_path], capture_output=True, text=True)
            error_list = []
            if result.returncode != 0:
                for line in result.stderr.split("\n"):
                    match = re.search(r"(.*\.java):(\d+): error: (.+)", line)
                    if match:
                        error_message = match.group(3)
                        if "is public, should be declared in a file named" in error_message:
                            continue
                        error_list.append({"lineNumber": int(match.group(2)), "message": error_message})
        finally:
            os.remove(temp_file_path)
        return error_list