ALTER TABLE "payments_history"
  RENAME TO "payment";

ALTER TABLE "scheduled_payment"
  ALTER COLUMN "description" TYPE text;