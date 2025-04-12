import httpx

from logical.logical_error_analyzer import LogicalErrorAnalyzerClient

NGROK_COLAB_URL = "https://0198-34-173-72-55.ngrok-free.app"

class LogicalErrorAnalyzerClientImpl(LogicalErrorAnalyzerClient):

    async def check_logical_errors(self, code_payload: dict) -> dict:
        async with httpx.AsyncClient() as client:
            try:
                response = await client.post(NGROK_COLAB_URL + "/detect-logical-errors",
                                             json=code_payload,
                                             timeout=300.0)
                response.raise_for_status()
                result = response.json()
                return {"success": True, "result": result}
            except httpx.HTTPError as e:
                return {"success": False, "error": str(e)}