from fastapi import FastAPI
from starlette.requests import Request
from starlette.responses import JSONResponse

from factory.syntax_analyzer_factory import SyntaxAnalyzerFactory
from logical.logical_error_analyzer_impl import LogicalErrorAnalyzerClientImpl

app = FastAPI()

@app.post("/detect-errors")
async def detect_errors(request: Request):
    payload = await request.json()
    print(payload)
    language = payload.get("language")
    code = payload.get("code")
    print(f"Language: {language}")
    print(f"Code: {code}")
    try:
        # Syntax Error Checking
        checker = SyntaxAnalyzerFactory.get_syntax_analyzer(language)
        syntax_errors = checker.check_syntax(code)

        # Logical Error Checking
        logical_error_checker = LogicalErrorAnalyzerClientImpl()
        client_response: dict = await logical_error_checker.check_logical_errors(code_payload=payload)
        if client_response.get("success", False):
            logical_errors: list[dict] = client_response.get("result", {}).get("errors")
            if len(logical_errors) > 0 and logical_errors[0].get("lineNumber") == -1:
                logical_errors = []
        else:
            logical_errors = []

        # Return the JSON response
        return JSONResponse(
            content={"syntax_errors": syntax_errors, "logical_errors": logical_errors},
            status_code=200
        )

    except ValueError as e:
        return JSONResponse(
            content={"error": str(e)},
            status_code=500
        )