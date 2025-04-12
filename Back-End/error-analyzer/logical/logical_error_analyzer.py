from abc import ABC, abstractmethod


class LogicalErrorAnalyzerClient(ABC):
    @abstractmethod
    async def check_logical_errors(self, code_payload: dict) -> dict:
        pass