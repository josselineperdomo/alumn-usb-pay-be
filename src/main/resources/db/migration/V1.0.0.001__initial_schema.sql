CREATE TABLE "beneficiary" (
  "id" int PRIMARY KEY,
  "name" int,
  "uphold_email" varchar UNIQUE,
  "uphold_username" varchar UNIQUE NOT NULL,
  "type_id" int,
  "active" boolean,
  "created_at" timestamp NOT NULL DEFAULT now(),
  "updated_at" timestamp
);

CREATE TABLE "type" (
  "id" int PRIMARY KEY,
  "name" varchar UNIQUE NOT NULL,
  "description" text,
  "default_amount_to_pay" float,
  "created_at" timestamp NOT NULL DEFAULT now()
);

CREATE TABLE "payments_history" (
  "id" int PRIMARY KEY,
  "beneficiary_id" int,
  "amount_paid" float NOT NULL,
  "paid_at" timestamp NOT NULL DEFAULT now()
);

CREATE TABLE "payment_list" (
  "id" int PRIMARY KEY,
  "name" varchar NOT NULL,
  "description" text,
  "created_at" timestamp NOT NULL DEFAULT now()
);

CREATE TABLE "payment_list_beneficiary" (
  "payment_list_id" int NOT NULL,
  "beneficiary_id" int NOT NULL,
  "amount_to_pay" float NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT now(),
  "updated_at" timestamp
);

CREATE TABLE "scheduled_payment" (
  "id" int PRIMARY KEY,
  "payment_list_id" int,
  "periodicity" interval NOT NULL,
  "active" boolean,
  "next_payment_at" timestamp,
  "last_executed_at" timestamp,
  "description" varchar,
  "created_at" timestamp NOT NULL DEFAULT now(),
  "updated_at" timestamp
);

ALTER TABLE "beneficiary" ADD FOREIGN KEY ("type_id") REFERENCES "type" ("id");

ALTER TABLE "payments_history" ADD FOREIGN KEY ("beneficiary_id") REFERENCES "beneficiary" ("id");

ALTER TABLE "payment_list_beneficiary" ADD FOREIGN KEY ("payment_list_id") REFERENCES "payment_list" ("id");

ALTER TABLE "payment_list_beneficiary" ADD FOREIGN KEY ("beneficiary_id") REFERENCES "beneficiary" ("id");

ALTER TABLE "scheduled_payment" ADD FOREIGN KEY ("payment_list_id") REFERENCES "payment_list" ("id");