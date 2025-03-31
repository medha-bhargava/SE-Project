# Base Class for Syntax Analysis
from abc import ABC, abstractmethod


class SyntaxAnalyzer(ABC):
    @abstractmethod
    def check_syntax(self, code: str):
        pass

