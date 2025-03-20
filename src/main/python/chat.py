from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from ctransformers import AutoModelForCausalLM
import chainlit as cl

app = FastAPI()

# Load the model
llm = AutoModelForCausalLM.from_pretrained(
    "zoltanctoth/orca_mini_3B-GGUF", model_file="orca-mini-3b.q4_0.gguf"
)

# Define a request model
class ChatRequest(BaseModel):
    message: str
    history: list[str] = []

# Define a response model
class ChatResponse(BaseModel):
    response: str

# Chat endpoint
@app.post("/chat", response_model=ChatResponse)
async def chat(chat_request: ChatRequest):
    try:
        # Generate a response using the model
        prompt = get_prompt(chat_request.message, chat_request.history)
        response = ""
        for word in llm(prompt, stream=True):
            response += word
        return ChatResponse(response=response)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Helper function to generate the prompt
def get_prompt(instruction: str, history: list[str] = None) -> str:
    system = "You are an AI assistant that gives helpful answers."
    prompt = f"### System:\n{system}\n\n### User:\n"
    if len(history) > 0:
        prompt += f"This is the conversation history: {''.join(history)}. Now answer the question:"
    prompt += f"{instruction}\n\n## Response:\n"
    return prompt

# Run the FastAPI server
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=5001)

"""
history = []

question = "can you answer me with frensh or you're just an english model "
answer = ""
for word in llm(get_prompt(question), stream=True):
    print(word, end="", flush=True)
    answer += word
print()
history.append(answer)
"""



