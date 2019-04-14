ALTER TABLE "type"
  RENAME TO "beneficiary_type";

ALTER TABLE "beneficiary_type"
  ADD COLUMN "updated_at" timestamp;