ALTER TABLE "uphold_card"
  ADD COLUMN "created_at" timestamp NOT NULL DEFAULT now(),
  ADD COLUMN "updated_at" timestamp;