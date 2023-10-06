import os
from dotenv import load_dotenv

load_dotenv()

# DB Connection Info
DB_URL=os.getenv("DB_URL")
DB_SCHMEA=os.getenv("DB_SCHMEA")
DB_USER=os.getenv("DB_USER")
DB_PWD=os.getenv("DB_PWD")