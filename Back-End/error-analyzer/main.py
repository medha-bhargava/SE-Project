from fastapi import FastAPI
from starlette.requests import Request
from starlette.responses import JSONResponse

from factory.syntax_analyzer_factory import SyntaxAnalyzerFactory

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

        # Semantic Error Checking (Only if no syntax errors)
        semantic_error = False
        # if not syntax_errors:
        #     semantic_error = semantic_checker.check_semantic_errors(request.code)

        return JSONResponse(
            content={"syntax_errors": syntax_errors, "semantic_errors": semantic_error},
            status_code=200
        )

    except ValueError as e:
        return JSONResponse(
            content={"error": str(e)},
            status_code=500
        )