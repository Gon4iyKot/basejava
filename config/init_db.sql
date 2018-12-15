create table resume
(
  uuid      varchar not null
    constraint resume_pkey
    primary key,
  full_name text     not null
);
create table contact
(
  id          serial   not null
    constraint contact_pkey
    primary key,
  type        text     not null,
  value       text     not null,
  resume_uuid varchar not null
    constraint contact_resume_uuid_fk
    references resume
    on delete cascade
);
create unique index contact_uuid_type_index
  on contact (resume_uuid, type);
CREATE TABLE public.section
(
  id serial PRIMARY KEY NOT NULL,
  section_type TEXT NOT NULL,
  content TEXT NOT NULL,
  resume_uuid varchar NOT NULL,
  CONSTRAINT section_resume_uuid_fk FOREIGN KEY (resume_uuid) REFERENCES public.resume (uuid) ON DELETE CASCADE
);
CREATE UNIQUE INDEX section_uuid_section_type_index ON public.section (resume_uuid, section_type);