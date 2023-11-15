--Delete "order" column from cv_skills table
ALTER TABLE cv_skills DROP COLUMN "order";
--Add "show_order" column to cv_skills table
ALTER TABLE cv_skills ADD COLUMN "show_order" INT NOT NULL DEFAULT 0;