FROM python:3.11.4-slim

RUN apt-get update && apt-get install -y default-libmysqlclient-dev pkg-config build-essential

COPY Pipfile ./
COPY Pipfile.lock ./

RUN python -m pip install --upgrade pip
RUN pip install pipenv && pipenv install wheel && pipenv install --dev --system --deploy

WORKDIR /coffeeing
COPY . .

EXPOSE 8000

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000", "--reload"]
